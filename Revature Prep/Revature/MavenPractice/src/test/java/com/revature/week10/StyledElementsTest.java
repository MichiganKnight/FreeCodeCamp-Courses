package com.revature.week10;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StyledElementsTest {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(StyledElementsTest.class.getName());
    private Process httpServerProcess;
    private String browserType;

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
    private static final boolean IS_ARM = OS_ARCH.contains("aarch64") || OS_ARCH.contains("arm");
    private static final boolean IS_WINDOWS = OS_NAME.contains("windows");
    private static final boolean IS_LINUX = OS_NAME.contains("linux");
    private static final boolean IS_MAC = OS_NAME.contains("mac");

    @BeforeEach
    public void setUp() {
        try {
            printEnvironmentInfo();

            BrowserConfig
        }
    }

    private boolean isPython3Available() {
        try {
            Process process = new ProcessBuilder("python3", "--version").start();
            boolean finished = process.waitFor(5, TimeUnit.SECONDS);

            if (finished && process.exitValue() == 0) {
                System.out.println("Python 3 is Available");

                return true;
            }
        } catch (Exception e) {
            System.err.println("Python 3 is Not Available: " + e.getMessage());
        }

        return false;
    }

    private String startHttpServer(File htmlFile) throws Exception {
        int port = 8080 + (int) (Math.random() * 1000);
        String directory = htmlFile.getParent();
        String fileName = htmlFile.getName();

        System.out.println("Starting HTTP Server on Port: " + port + "...");

        String pythonCmd = IS_WINDOWS ? "python" : "python3";
        ProcessBuilder builder = new ProcessBuilder(pythonCmd, "-m", "http.server", String.valueOf(port));
        builder.directory(new File(directory));
        builder.redirectErrorStream(true);

        httpServerProcess = builder.start();

        Thread.sleep(3000);

        if (!httpServerProcess.isAlive()) {
            throw new RuntimeException("HTTP Server Failed to Start");
        }

        String url = "http://localhost:" + port + "/" + fileName;

        for (int i = 0; i < 10; i++) {
            try {
                URL testUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) testUrl.openConnection();

                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);
                connection.setRequestMethod("HEAD");

                int responseCode = connection.getResponseCode();

                connection.disconnect();
                if (responseCode == 200) {
                    System.out.println("HTTP Server Ready: " + url);

                    return url;
                }
            } catch (Exception e) {
                if (i == 9) {
                    throw new RuntimeException("HTTP Server Not Responding: " + e.getMessage());
                }

                Thread.sleep(1000);
            }
        }

        throw new RuntimeException("HTTP Server Failed to Respond");
    }

    private WebDriver createWebDriver(BrowserConfig config) {
        System.out.println("\n=== CREATING WEB DRIVER ===");
        System.out.println("Browser: " + config.browserType);
        System.out.println("Driver Path: " + config.driverPath);
        System.out.println("Binary Path: " + config.binaryPath);

        if ("edge".equals(config.browserType)) {
            return createEdgeDriver(config);
        } else {
            return createChromeDriver(config);
        }
    }

    private WebDriver createChromeDriver(BrowserConfig config) {
        System.setProperty("webdriver.chrome.driver", config.driverPath);
        ChromeOptions chromeOptions = new ChromeOptions();

        if (config.binaryPath != null) {
            chromeOptions.setBinary(config.binaryPath);
        }

        chromeOptions.addArguments(getChromeArguments());

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        chromeOptions.setCapability("ms:loggingPrefs", loggingPreferences);

        ChromeDriverService.Builder serviceBuilder = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(config.driverPath))
                .withTimeout(Duration.ofSeconds(30));

        ChromeDriverService service = serviceBuilder.build();

        return new ChromeDriver(service, chromeOptions);
    }

    private WebDriver createEdgeDriver(BrowserConfig config) {
        System.setProperty("webdriver.edge.driver", config.driverPath);
        EdgeOptions edgeOptions = new EdgeOptions();

        if (config.binaryPath != null) {
            edgeOptions.setBinary(config.binaryPath);
        }

        edgeOptions.addArguments(getEdgeArguments());

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        edgeOptions.setCapability("ms:loggingPrefs", loggingPreferences);

        EdgeDriverService.Builder serviceBuilder = new EdgeDriverService.Builder()
                .usingDriverExecutable(new File(config.driverPath))
                .withTimeout(Duration.ofSeconds(30));

        EdgeDriverService service = serviceBuilder.build();

        return new EdgeDriver(service, edgeOptions);
    }

    private String[] getChromeArguments() {
        return getCommonBrowserArguments();
    }

    private String[] getEdgeArguments() {
        return getCommonBrowserArguments();
    }

    private String[] getCommonBrowserArguments() {
        String[] baseArgs = {
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080",
                "--disable-extensions",
                "--disable-web-security",
                "--allow-file-access-from-files",
                "--allow-running-insecure-content",
                "--user-data-dir=/tmp/browser-test-" + System.currentTimeMillis(),
                "--disable-features=TranslateUI,VizDisplayCompositor",
                "--disable-background-timer-throttling",
                "--disable-backgrounding-occluded-windows",
                "--disable-renderer-backgrounding"
        };
    }

    private void printPageInfo() {
        System.out.println("=== Page Info ===");
        System.out.println("Page Title: " + webDriver.getTitle());
        System.out.println("Page URL: " + webDriver.getCurrentUrl());
        System.out.println("Page Source Length: " + webDriver.getPageSource().length());
    }

    private void stopHttpServer() {
        if (httpServerProcess != null) {
            try {
                System.out.println("Stopping HTTP Server...");
                httpServerProcess.destroy();

                boolean terminated = httpServerProcess.waitFor(5, TimeUnit.SECONDS);
                if (!terminated) {
                    httpServerProcess.destroyForcibly();
                }

                httpServerProcess = null;
                System.out.println("HTTP Server Stopped");
            } catch (Exception e) {
                System.err.println("Error Stopping HTTP Server: " + e.getMessage());
                try {
                    httpServerProcess.destroyForcibly();
                } catch (Exception ignored) {

                }

                httpServerProcess = null;
            }
        }
    }

    private void cleanup() {
        stopHttpServer();
        if (webDriver != null) {
            try {
                webDriver.quit();
                webDriver = null;
            } catch (Exception e) {
                System.err.println("Error Cleaning up WebDriver: " + e.getMessage());
            }
        }
    }

    @AfterEach
    public void tearDown() {
        System.out.println("\n=== TEARDOWN ===");
        cleanup();
        System.out.println("=== Teardown Complete ===");
    }

    private static class BrowserConfig {
        final String browserType;
        final String driverPath;
        final String binaryPath;

        BrowserConfig(String browserType, String driverPath, String binaryPath) {
            this.browserType = browserType;
            this.driverPath = driverPath;
            this.binaryPath = binaryPath;
        }
    }
}
