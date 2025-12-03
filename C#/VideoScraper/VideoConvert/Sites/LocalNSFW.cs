using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Diagnostics.CodeAnalysis;
using System.Text.RegularExpressions;
using Newtonsoft.Json;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.DevTools;
using OpenQA.Selenium.DevTools.V141.Network;
using OpenQA.Selenium.Support.UI;
using VideoConvert.JSON;
using DevToolsSessionDomains = OpenQA.Selenium.DevTools.V141.DevToolsSessionDomains;

namespace VideoConvert
{
    [SuppressMessage("ReSharper", "InconsistentNaming")]
    public static class LocalNSFW
    {
        private static readonly string? ProjectRoot = Path.GetDirectoryName(Path.GetDirectoryName(Path.GetDirectoryName(Path.GetDirectoryName(AppContext.BaseDirectory))));

        private static readonly string DataDir = Path.Combine(ProjectRoot, "wwwroot", "Data");
        private static readonly string VideoDir = Path.Combine(ProjectRoot, "wwwroot", "Videos", "NSFW");
        private static readonly string VideoFile = Path.Combine(DataDir, "LocalNSFW.json");

        private static readonly HttpClient HttpClient = new();

        public static async Task GetVideos(List<(string url, string title)> urls)
        {
            if (!Directory.Exists(DataDir))
                Directory.CreateDirectory(DataDir);

            List<LocalVideo> allVideos = [];

            if (File.Exists(VideoFile))
            {
                try
                {
                    string json = await File.ReadAllTextAsync(VideoFile);
                    if (!string.IsNullOrWhiteSpace(json))
                    {
                        allVideos = JsonConvert.DeserializeObject<List<LocalVideo>>(json) ?? [];   
                    }
                }
                catch (JsonException)
                {
                    Logger.LogMessage(LogType.Error, "Invalid JSON Detected - Starting Over");
                    allVideos = [];
                }
            }

            const int maxConcurrent = 2;
            SemaphoreSlim semaphore = new(maxConcurrent);

            List<Task> tasks = urls.Select(async video =>
            {
                await semaphore.WaitAsync();
                try
                {
                    string safeTitle = Path.GetInvalidFileNameChars().Aggregate(video.title, (current, c) => current.Replace(c, '_'));
                    string outputFile = Path.Combine(VideoDir, $"{safeTitle}.mp4");

                    if (File.Exists(outputFile))
                    {
                        Logger.LogMessage(LogType.Info, $"Output File Exists: {outputFile}");
                        return;
                    }

                    LocalVideo? videoData = await CaptureAndConvertAsync(video);

                    if (videoData == null)
                    {
                        Logger.LogMessage(LogType.Info, $"Skipping Video: {video.title}");
                        return;
                    }

                    LocalVideo? existing = allVideos.FirstOrDefault(v => string.Equals(v.Name, videoData.Name, StringComparison.OrdinalIgnoreCase));
                    if (existing != null)
                    {
                        if (existing.Source != videoData.Source)
                        {
                            existing.Source = videoData.Source;
                            Logger.LogMessage(LogType.Info, $"Updated Source for: {existing.Name}");
                        }
                    }
                    else
                    {
                        allVideos.Add(videoData);
                        Logger.LogMessage(LogType.Success, $"Added New: {videoData.Name}");
                    }
                }
                catch (Exception ex)
                {
                    Logger.LogMessage(LogType.Error, $"Failed to Process {video.title}: {ex.Message}");
                }
                finally
                {
                    semaphore.Release();
                }
            }).ToList();

            await Task.WhenAll(tasks);

            await File.WriteAllTextAsync(VideoFile, JsonConvert.SerializeObject(allVideos, Formatting.Indented));
            
            Logger.LogMessage(LogType.Success, $"All Changes Saved to: {VideoFile}\n");
            Logger.LogMessage(LogType.Success, "All Videos Processed!");
        }

        private static async Task<LocalVideo?> CaptureAndConvertAsync((string url, string title) video)
        {
            string safeTitle = Path.GetInvalidFileNameChars().Aggregate(video.title, (current, c) => current.Replace(c, '_'));
            string outputFile = Path.Combine(VideoDir, $"{safeTitle}.mp4");

            if (File.Exists(outputFile))
            {
                Logger.LogMessage(LogType.Info, $"Output File Exists: {outputFile}");
                return null;
            }

            ChromeOptions options = new();
            options.AddArgument("--headless");
            options.AddArgument("--disable-gpu");
            options.AddArgument("--disable-notifications");
            options.AddArgument("--disable-blink-features=AutomationControlled");

            using ChromeDriver driver = new(options);
            DevToolsSession devTools = driver.GetDevToolsSession();
            DevToolsSessionDomains domains = devTools.GetVersionSpecificDomains<DevToolsSessionDomains>();
            domains.Network.Enable(new EnableCommandSettings()).Wait();

            List<string> m3u8Urls = [];
            List<string> logBuffer = [];

            void RequestHandler(object? sender, RequestWillBeSentEventArgs e)
            {
                if (e.Request.Url.EndsWith(".m3u8", StringComparison.OrdinalIgnoreCase))
                {
                    lock (m3u8Urls)
                    {
                        if (!m3u8Urls.Contains(e.Request.Url))
                        {
                            m3u8Urls.Add(e.Request.Url);
                            logBuffer.Add($"[M3U8 Found]: {e.Request.Url}");
                        }
                    }
                }
            }

            domains.Network.RequestWillBeSent += RequestHandler;

            try
            {
                await driver.Navigate().GoToUrlAsync(video.url);

                WebDriverWait wait = new(driver, TimeSpan.FromSeconds(20));
                wait.Until(d => ((IJavaScriptExecutor)d).ExecuteScript("return document.readyState")?.ToString() == "complete");

                await Task.Delay(3000); // Allow dynamic content to load

                if (video.url.Contains("eporner"))
                {
                    WebDriverWait wait2 = new(driver, TimeSpan.FromSeconds(10));
                    IWebElement element = wait2.Until(d => d.FindElement(By.ClassName("vjs-big-play-button")));
                    
                    
                }
                
                if (m3u8Urls.Count == 0)
                {
                    logBuffer.Add("[Error]: No M3U8 URLs Found");
                    FlushLogs(logBuffer);
                    return null;
                }

                string masterUrl = m3u8Urls.First();
                logBuffer.Add($"Processing Video: {video.title}");
                logBuffer.Add($"Master M3U8: {masterUrl}");
                logBuffer.Add($"Output: {outputFile}");

                List<string> tags = await GetTagsAsync(driver, video.url, wait);

                string selectedVariant = await GetHighestResolutionVariant(masterUrl, logBuffer);
                Logger.LogMessage(LogType.Info, $"Selected Variant: {selectedVariant}");
                await ConvertWithFFmpeg(selectedVariant, outputFile, logBuffer);

                FlushLogs(logBuffer);

                return new LocalVideo
                {
                    Name = safeTitle,
                    Source = $"/Videos/{Path.GetFileName(outputFile)}",
                    Tags = tags,
                    Positions = []
                };
            }
            catch (Exception ex)
            {
                logBuffer.Add($"[Error]: Failed to process {video.title}: {ex.Message}");
                FlushLogs(logBuffer);
                return null;
            }
            finally
            {
                domains.Network.RequestWillBeSent -= RequestHandler;
                driver.Quit();
            }
        }

        private static async Task<List<string>> GetTagsAsync(ChromeDriver driver, string url, WebDriverWait wait)
        {
            List<string> tags = [];
            try
            {
                if (url.Contains("hdefporn"))
                {
                    ReadOnlyCollection<IWebElement> container = wait.Until(d =>
                    {
                        ReadOnlyCollection<IWebElement> elems = d.FindElements(By.ClassName("item-tags"));
                        return elems.Count > 0 && elems[0].FindElements(By.TagName("a")).Count > 0 ? elems : null;
                    });

                    tags.AddRange(container[0].FindElements(By.TagName("a")).Select(tag => tag.Text));
                }
                else if (url.Contains("xvideos"))
                {
                    ReadOnlyCollection<IWebElement> container = wait.Until(d =>
                    {
                        ReadOnlyCollection<IWebElement> elems = d.FindElements(By.ClassName("video-tags-list"));
                        return elems.Count > 0 && elems[0].FindElements(By.ClassName("is-keyword")).Count > 0 ? elems : null;
                    });

                    tags.AddRange(container[0].FindElements(By.ClassName("is-keyword")).Select(tag => tag.Text));
                }
            }
            catch (WebDriverTimeoutException)
            {
                Logger.LogMessage(LogType.Info, $"No tags found for: {url}");
            }

            await Task.Delay(100);
            return tags;
        }

        private static void FlushLogs(List<string> buffer)
        {
            foreach (string msg in buffer)
            {
                Logger.LogMessage(msg.StartsWith("[Error]") ? LogType.Error : msg.StartsWith("[Success]") ? LogType.Success : LogType.Info, msg);   
            }
        }

        private static async Task<string> GetHighestResolutionVariant(string masterUrl, List<string> logBuffer)
        {
            string sanitizedMasterUrl = masterUrl.Replace(" ", "%20");

            string playlist = await HttpClient.GetStringAsync(sanitizedMasterUrl);

            Regex regex = new(@"#EXT-X-STREAM-INF:.*RESOLUTION=(\d+)x(\d+).*?\n(.*)", RegexOptions.Multiline);
            MatchCollection matches = regex.Matches(playlist);

            if (matches.Count == 0)
            {
                logBuffer.Add("[Warning]: No resolution variants found, using master playlist");
                return masterUrl;
            }

            var variants = matches
                .Select(m => new
                {
                    Height = int.Parse(m.Groups[2].Value),
                    Url = m.Groups[3].Value.Trim()
                })
                .OrderByDescending(v => v.Height)
                .ToList();

            string selected = variants.First().Url;
            
            if (!selected.StartsWith("http"))
            {
                Uri baseUri = new(masterUrl);
                selected = new Uri(baseUri, selected).ToString();
            }
            
            selected = selected
                .Replace(",", "%2C")
                .Replace("=", "%3D")
                .Replace(" ", "%20");

            logBuffer.Add($"[Selected {variants.First().Height}p Stream]: {selected}");
            return selected;
        }

        private static async Task ConvertWithFFmpeg(string inputUrl, string outputFile, List<string> logBuffer)
        {
            logBuffer.Add("[FFmpeg]: Starting Fast Conversion...");

            string args = $"-y -hide_banner -loglevel info " +
                          $"-fflags nobuffer -flags low_delay -analyzeduration 10M -probesize 50M " +
                          $"-rw_timeout 3000000 -reconnect 1 -reconnect_streamed 1 -reconnect_delay_max 2 " +
                          $"-protocol_whitelist file,http,https,tcp,tls,crypto " +
                          $"-i \"{inputUrl}\" -vf scale=\"trunc(iw*720/ih/2)*2:720\" -c:v libx265 -preset ultrafast -crf 28 -c:a copy -fps_mode passthrough \"{outputFile}\"";

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

                    Logger.LogMessage(LogType.Info, $"\rProgress: {percent:F1}% | ETA: {TimeSpan.FromSeconds(remaining):mm\\:ss} | Elapsed: {TimeSpan.FromSeconds(elapsed):mm\\:ss}  ");
                }
            }

            await process.WaitForExitAsync();
            Console.WriteLine();

            logBuffer.Add(process.ExitCode == 0 ? $"[Success]: Conversion Complete: {outputFile}" : $"[Error]: FFmpeg Failed with Exit Code: {process.ExitCode}");
        }
    }
}