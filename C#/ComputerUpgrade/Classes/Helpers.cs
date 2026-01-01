using System.Collections.ObjectModel;
using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Support.UI;
using SeleniumExtras.WaitHelpers;
using VideoConvert;
using LogType = VideoConvert.LogType;

namespace ComputerUpgrade.Classes
{
    public abstract class Helpers
    {
        public static bool CheckAvailability(string url)
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

            driver.Navigate().GoToUrl(url);

            Logger.LogMessage(LogType.Info, $"Loading Site: {url}");
            
            WebDriverWait wait = new(driver, TimeSpan.FromSeconds(10));
            ReadOnlyCollection<IWebElement> inventoryElement = wait.Until(ExpectedConditions.PresenceOfAllElementsLocatedBy(By.Id("pnlInventory")));
            Thread.Sleep(2);
            
            IWebElement availableElement = inventoryElement[0].FindElement(By.ClassName("inventoryCnt"));
            
            string updatedText = availableElement.Text.Contains('+') ? availableElement.Text.Replace("+", "") : availableElement.Text;
            
            int count = int.Parse(updatedText.Replace(" NEW IN STOCK", ""));

            return count >= 1;
        }
    }
}