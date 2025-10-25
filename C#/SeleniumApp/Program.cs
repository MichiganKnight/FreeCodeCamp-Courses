using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;
using System.Text.RegularExpressions;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.DevTools;
using OpenQA.Selenium.DevTools.V141.Network;
using DevToolsSessionDomains = OpenQA.Selenium.DevTools.V141.DevToolsSessionDomains;

namespace SeleniumApp
{
    [SuppressMessage("ReSharper", "InconsistentNaming")]
    public class Program
    {
        public static async Task Main(string[] args)
        {
            await CaptureAndConvert();
        }

        private static async Task CaptureAndConvert()
        {
            ChromeOptions options = new();
            options.AddArgument("--headless");
            options.AddArgument("--disable-gpu");
            options.AddArgument("--disable-notifications");
            options.AddArgument("--disable-blink-features=AutomationControlled");

            using ChromeDriver driver = new(options);

            DevToolsSession devTools = driver.GetDevToolsSession();
            DevToolsSessionDomains domains = devTools.GetVersionSpecificDomains<DevToolsSessionDomains>();

            List<string> m3u8Urls = [];

            await domains.Network.Enable(new EnableCommandSettings());

            domains.Network.RequestWillBeSent += (sender, e) =>
            {
                if (e.Request.Url.Contains(".m3u8"))
                {
                    lock (m3u8Urls)
                    {
                        if (!m3u8Urls.Contains(e.Request.Url))
                        {
                            m3u8Urls.Add(e.Request.Url);
                            Console.ForegroundColor = ConsoleColor.Green;
                            Console.WriteLine($"[M3U8 Found]: {e.Request.Url}");
                            Console.ResetColor();
                        }
                    }
                }
            };

            //string url = "https://hdefporn.com/i/90103/real-sex-party-on-the-sunny-beach-part-3";
            string url = "https://hdefporn.com/i/69140/euro-trash-hardcore";

            await driver.Navigate().GoToUrlAsync(url);
            await Task.Delay(TimeSpan.FromSeconds(15));

            if (m3u8Urls.Count == 0)
            {
                Console.WriteLine("No M3U8 URLs Found");
                driver.Quit();
                return;
            }

            string masterUrl = m3u8Urls.First();
            string title = driver.Title;
            driver.Quit();

            title = Path.GetInvalidFileNameChars().Aggregate(title, (current, c) => current.Replace(c, '_'));

            string outputDir = Path.Combine(AppContext.BaseDirectory, "Videos");
            Directory.CreateDirectory(outputDir);

            string outputFile = Path.Combine(outputDir, $"{title}.mp4");

            if (File.Exists(outputFile))
            {
                Console.WriteLine($"Output File Exists: {outputFile}");
                return;
            }

            Console.WriteLine($"\nMaster M3U8: {masterUrl}");
            Console.WriteLine($"Output File: {outputFile}\n");

            string? selectedVariant = await GetVariantForResolution(masterUrl, "720");

            if (selectedVariant == null)
            {
                Console.WriteLine("No 720p Variant Found — Using Master Playlist Instead");
                selectedVariant = masterUrl;
            }

            await ConvertWithFFmpeg(selectedVariant, outputFile);
        }

        private static async Task ConvertWithFFmpeg(string inputUrl, string outputFile)
        {
            string args =
                $"-y -hide_banner -loglevel info " +
                $"-fflags nobuffer -flags low_delay -analyzeduration 0 -probesize 32 " +
                $"-rw_timeout 3000000 -reconnect 1 -reconnect_streamed 1 -reconnect_delay_max 2 " +
                $"-i \"{inputUrl}\" -vf scale=-1:720 -c:v libx265 -preset ultrafast -crf 28 -c:a copy -vsync 0 " +
                $"\"{outputFile}\"";

            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine($"[FFmpeg] Starting Fast Conversion...");
            Console.ResetColor();

            using Process process = new();
            process.StartInfo = new ProcessStartInfo
            {
                FileName = "ffmpeg",
                Arguments = args,
                UseShellExecute = false,
                RedirectStandardError = true,
                CreateNoWindow = true
            };

            process.Start();

            DateTime startTime = DateTime.Now;
            TimeSpan? totalDuration = null;

            Regex regexDuration = new(@"Duration:\s(\d+):(\d+):(\d+.\d+)");
            Regex regexTime = new(@"time=(\d+):(\d+):(\d+.\d+)");

            while (!process.StandardError.EndOfStream)
            {
                string? line = await process.StandardError.ReadLineAsync();
                if (line == null) continue;

                if (regexDuration.IsMatch(line))
                {
                    Match m = regexDuration.Match(line);
                    totalDuration = new TimeSpan(0, int.Parse(m.Groups[1].Value),
                        int.Parse(m.Groups[2].Value),
                        (int)Math.Floor(double.Parse(m.Groups[3].Value)));
                }

                if (regexTime.IsMatch(line) && totalDuration.HasValue)
                {
                    Match m = regexTime.Match(line);
                    TimeSpan current = new TimeSpan(0, int.Parse(m.Groups[1].Value),
                        int.Parse(m.Groups[2].Value),
                        (int)Math.Floor(double.Parse(m.Groups[3].Value)));

                    double percent = (current.TotalSeconds / totalDuration.Value.TotalSeconds) * 100.0;
                    double elapsed = (DateTime.Now - startTime).TotalSeconds;
                    double estimatedTotal = elapsed / (percent / 100.0);
                    double remaining = estimatedTotal - elapsed;

                    Console.Write($"\rProgress: {percent:F1}% | ETA: {TimeSpan.FromSeconds(remaining):mm\\:ss} | " +
                                  $"Elapsed: {TimeSpan.FromSeconds(elapsed):mm\\:ss}  ");
                }
            }

            await process.WaitForExitAsync();

            Console.WriteLine();
            if (process.ExitCode == 0)
            {
                Console.ForegroundColor = ConsoleColor.Green;
                Console.WriteLine($"Conversion complete: {outputFile}");
            }
            else
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine($"FFmpeg failed with exit code {process.ExitCode}");
            }

            Console.ResetColor();
        }

        private static async Task<string?> GetVariantForResolution(string masterUrl, string resolutionKeyword)
        {
            using HttpClient client = new();
            string playlist = await client.GetStringAsync(masterUrl);

            Regex regex = new(@"#EXT-X-STREAM-INF:.*RESOLUTION=(\d+)x(\d+).*?\n(.*)", RegexOptions.Multiline);
            MatchCollection matches = regex.Matches(playlist);

            if (matches.Count == 0)
                return null;

            foreach (Match m in matches)
            {
                string height = m.Groups[2].Value;
                string streamUrl = m.Groups[3].Value.Trim();

                if (height.Contains(resolutionKeyword))
                {
                    if (!streamUrl.StartsWith("http"))
                    {
                        Uri baseUri = new(masterUrl);
                        streamUrl = new Uri(baseUri, streamUrl).ToString();
                    }

                    Console.WriteLine($"[Selected 720p Stream]: {streamUrl}");
                    return streamUrl;
                }
            }

            return null;
        }
    }
}