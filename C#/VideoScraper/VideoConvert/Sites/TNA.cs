using System.Collections.ObjectModel;
using Newtonsoft.Json;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using SeleniumExtras.WaitHelpers;
using VideoConvert.JSON;
using LogType = VideoConvert.LogType;

namespace VideoConvert.Sites
{
    public abstract class TNA
    {
        private static readonly string? ProjectRoot = Path.GetDirectoryName(Path.GetDirectoryName(Path.GetDirectoryName(Path.GetDirectoryName(AppContext.BaseDirectory))));
        private static readonly string DataDir = Path.Combine(ProjectRoot, "wwwroot", "Data");
        private static readonly string VideosFile = Path.Combine(DataDir, "TNA.json");

        public static void GetVideos(List<(string, string)> tnaUrls)
        {
            if (!Directory.Exists(DataDir))
            {
                Directory.CreateDirectory(DataDir);   
            }
            
            List<Video>? allVideos = [];

            if (File.Exists(VideosFile))
            {
                try
                {
                    string json = File.ReadAllText(VideosFile);

                    if (!string.IsNullOrWhiteSpace(json))
                    {
                        allVideos = JsonConvert.DeserializeObject<List<Video>>(json) ?? [];
                    }
                }
                catch (JsonException e)
                {
                    Console.WriteLine(e.Message);
                    Logger.LogMessage(LogType.Error, "Invalid JSON Detected - Starting Over");
                    
                    allVideos = [];
                }
            }

            bool hasChanges = false;

            foreach ((string, string) url in tnaUrls)
            {
                Video? videoData = ScrapeVideo(url);

                if (videoData == null)
                {
                    continue;
                }
                
                Video? existing = allVideos.FirstOrDefault(v => string.Equals(v.Name, videoData.Name, StringComparison.OrdinalIgnoreCase));
                
                if (existing != null)
                {
                    if (existing.Source != videoData.Source || existing.Poster != videoData.Poster)
                    {
                        existing.Source = videoData.Source;
                        existing.Poster = videoData.Poster;
                        
                        Logger.LogMessage(LogType.Info, $"Updated Source for: {existing.Name}");
                        
                        hasChanges = true;
                    }
                }
                else
                {
                    allVideos.Add(videoData);
                    
                    Logger.LogMessage(LogType.Success, $"Added New: {videoData.Name}");
                    
                    hasChanges = true;
                }
            }

            if (hasChanges)
            {
                File.WriteAllText(VideosFile, JsonConvert.SerializeObject(allVideos, Formatting.Indented));
                
                Logger.LogMessage(LogType.Success, $"Changes Saved to: {VideosFile}\n");
            }
            else
            {
                Logger.LogMessage(LogType.Info, "No Changes Detected\n");
            }
        }

        private static Video ScrapeVideo((string url, string title) url)
        {
            ChromeOptions options = new();
            options.AddArgument("--headless");
            options.AddArgument("--disable-gpu");
            options.AddArgument("--disable-notifications");
            options.AddArgument("--disable-blink-features=AutomationControlled");

            options.AddExcludedArgument("enable-automation");

            using ChromeDriver driver = new(options);

            IJavaScriptExecutor javaScriptExecutor = driver;
            javaScriptExecutor.ExecuteScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
            javaScriptExecutor.ExecuteScript("Object.defineProperty(navigator, 'languages', {get: () => ['en-US', 'en']})");
            javaScriptExecutor.ExecuteScript("Object.defineProperty(navigator, 'vendor', {get: () => 'Google Inc.'})");
            javaScriptExecutor.ExecuteScript("Object.defineProperty(navigator, 'platform', {get: () => 'Win32'})");

            driver.Navigate().GoToUrl(url.url);

            string title = url.title;

            Logger.LogMessage(LogType.Info, $"Loading Video: {title}");

            WebDriverWait wait = new(driver, TimeSpan.FromSeconds(10));
            ReadOnlyCollection<IWebElement> videoElement = wait.Until(ExpectedConditions.PresenceOfAllElementsLocatedBy(By.Id("video-player")));
            Thread.Sleep(2);

            string? poster = videoElement[0].GetAttribute("poster");
            ReadOnlyCollection<IWebElement> sourceElements = videoElement[0].FindElements(By.TagName("source"));

            int maxSize = 0;

            string bestSource = "";

            foreach (IWebElement sourceElement in sourceElements)
            {
                string? sizeAttr = sourceElement.GetAttribute("size");
                try
                {
                    int size = int.Parse(sizeAttr?.Replace("p", "") ?? string.Empty);
                    if (size > maxSize)
                    {
                        maxSize = size;
                        bestSource = sourceElement.GetAttribute("src") ?? "";
                    }
                }
                catch (Exception e)
                {
                    continue;
                }
            }

            driver.Quit();

            if (bestSource != "")
            {
                return new Video()
                {
                    Name = title,
                    Source = bestSource,
                    Poster = poster,
                    Positions = []
                };
            }
            else
            {
                Logger.LogMessage(LogType.Info, $"No Source Found: {title}");
                
                return null;
            }
        }
    }
}