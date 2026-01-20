package com.revature.Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class TestSetup {
    public static WebDriver webDriver;
    public static ClientAndServer mockServer;
    public static MockServerClient mockServerClient;

    private static Process httpServerProcess;
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
    private static final boolean IS_ARM = OS_ARCH.contains("aarch64") || OS_ARCH.contains("arm");
    private static final boolean IS_WINDOWS = OS_NAME.contains("windows");
    private static final boolean IS_MAC = OS_NAME.contains("mac");

    public static void printEnvironmentInfo() {
        System.out.println("=== ENVIRONMENT INFO ===");
        System.out.println("OS: " + OS_NAME + " (" + OS_ARCH + ")");
        System.out.println("Architecture: " + (IS_ARM ? "ARM64" : "x86/x64"));
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
    }

    public static String detectBrowser() {
        System.out.println("\n=== BROWSER DETECTION ===");

        if (findChromeBinary() != null) {
            return "chrome";
        } else if (findEdgeBinary() != null) {
            return "edge";
        }

        throw new RuntimeException("No Compatible Browser Driver Found");
    }

    private static String findChromeBinary() {
        String[] chromePaths;

        if (IS_WINDOWS) {
            chromePaths = new String[]{
                    "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                    "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
            };
        } else if (IS_MAC) {
            chromePaths = new String[]{"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"};
        } else {
            chromePaths = new String[]{
                    "/usr/bin/chromium-browser",
                    "/usr/bin/chromium",
                    "/usr/bin/google-chrome",
                    "/snap/bin/chromium"
            };
        }

        for (String path : chromePaths) {
            if (new File(path).exists()) {
                System.out.println("Found Chrome Binary: " + path);

                return path;
            }
        }

        System.err.println("No Chrome Binary Found");
        return null;
    }

    private static String findEdgeBinary() {
        if (IS_WINDOWS) {
            String[] edgePaths = {
                    "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe",
                    "C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe"
            };

            for (String path : edgePaths) {
                if (new File(path).exists()) {
                    System.out.println("Found Edge Binary: " + path);

                    return path;
                }
            }
        }

        System.err.println("No Edge Binary Found");
        return null;
    }

    public static File findhtmlFile() {
        String[] possibleHtmlPaths = {
                "src/main/resources/public/frontend/register/register-page.html",
                "register-page.html",
                "src/test/resources/register-page.html",
                "test-resources/register-page.html",
                "src/main/register-page.html"
        };

        for (String htmlPath : possibleHtmlPaths) {
            File testFile = new File(htmlPath);
            if (testFile.exists()) {
                System.out.println("Found HTML File: " + testFile.getAbsolutePath());

                return testFile;
            }
        }

        throw new RuntimeException("Could not Find 'register-page.html' in Expected Location: " + Arrays.toString(possibleHtmlPaths));
    }

    public static String determineHtmlUrl(File htmlFile) {
        if (isPython3Available()) {
            try {
                return startHttpServer(htmlFile);
            } catch (Exception e) {
                System.err.println("Error Starting HTTP Server: " + e.getMessage());
            }
        } else {
            System.out.println("Python3 not Available, Using File URL");
        }

        return "file://" + htmlFile.getAbsolutePath();
    }

    private static boolean isPython3Available() {
        try {
            Process process = new ProcessBuilder("python3", "--version").start();
            boolean finished = process.waitFor(5, TimeUnit.SECONDS);
            if (finished && process.exitValue() == 0) {
                System.out.println("Python3 is Available");

                return true;
            }
        } catch (Exception ignored) {
        }

        if (IS_WINDOWS) {
            try {
                Process process = new ProcessBuilder("python", "--version").start();
                boolean finished = process.waitFor(5, TimeUnit.SECONDS);
                if (finished && process.exitValue() == 0) {
                    System.out.println("Python3 is Available");

                    return true;
                }
            } catch (Exception ignored) {
            }
        }

        System.out.println("Python3 is Not Available");
        return false;
    }

    private static String startHttpServer(File htmlFile) throws Exception {
        int port = 8000 + (int) (Math.random() * 1000);
        String directory = htmlFile.getParent();
        String fileName = htmlFile.getName();

        System.out.println("Starting HTTP Server on Port: " + port);

        String pythonCommand = IS_WINDOWS ? "python" : "python3";
        ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand, "-m", "http.server", String.valueOf(port));
        processBuilder.directory(new File(directory));
        processBuilder.redirectErrorStream(true);

        httpServerProcess = processBuilder.start();

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

    public static WebDriver createWebDriver(String browserType) {
        System.out.println("\n=== CREATING WEBDRIVER ===");
        System.out.println("Browser: " + browserType);

        if ("edge".equals(browserType)) {
            WebDriverManager.edgedriver().setup();
            webDriver = createEdgeDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            webDriver = createChromeDriver();
        }

        return webDriver;
    }

    private static WebDriver createEdgeDriver() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments(getEdgeArguments());

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        edgeOptions.setCapability("ms:loggingPrefs", loggingPreferences);

        return new EdgeDriver(edgeOptions);
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(getChromeArguments());

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        chromeOptions.setCapability("goog:loggingPrefs", loggingPreferences);

        return new ChromeDriver(chromeOptions);
    }

    private static String[] getEdgeArguments() {
        return getCommonBrowserArguments();
    }

    private static String[] getChromeArguments() {
        return getCommonBrowserArguments();
    }

    private static String[] getCommonBrowserArguments() {
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
                //"--user-data-dir=/tmp/browser-test-" + System.currentTimeMillis(),
                "--disable-features=TranslateUI,VizDisplayCompositor",
                "--disable-background-timer-throttling",
                "--disable-backgrounding-occluded-windows",
                "--disable-renderer-backgrounding"
        };

        if (IS_ARM) {
            String[] armArgs = {
                    "--disable-features=VizDisplayCompositor",
                    "--use-gl=swiftshader",
                    "--disable-software-rasterizer"
            };

            String[] combined = new String[baseArgs.length + armArgs.length];
            System.arraycopy(baseArgs, 0, combined, 0, baseArgs.length);
            System.arraycopy(armArgs, 0, combined, baseArgs.length, armArgs.length);

            return combined;
        }

        return baseArgs;
    }

    public static void printPageInfo() {
        System.out.println("Page Title: " + webDriver.getTitle());
        System.out.println("Current URL: " + webDriver.getCurrentUrl());
        System.out.println("Page Source Length: " + webDriver.getPageSource().length());
    }

    public static void stopHttpServer() {
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

    public static void cleanup() throws InterruptedException {
        stopHttpServer();

        if (webDriver != null) {
            try {
                webDriver.quit();
                webDriver = null;
            } catch (Exception e) {
                System.err.println("Error Quitting WebDriver: " + e.getMessage());
            }
        }

        if (mockServer != null) {
            try {
                mockServer.stop();

                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Error Stopping MockServer: " + e.getMessage());
            } finally {
                mockServer = null;
            }
        }

        if (mockServerClient != null) {
            mockServerClient.close();
            mockServerClient = null;
        }
    }
}
