package com.revature;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class RegisterTest {
    private WebDriver webDriver;
    private WebDriverWait wait;
    private ClientAndServer mockServer;
    private MockServerClient mockServerClient;
    private Process httpServerProcess;
    private String browserType;

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
    private static final boolean IS_ARM = OS_ARCH.contains("aarch64") || OS_ARCH.contains("arm");
    private static final boolean IS_WINDOWS = OS_NAME.contains("windows");
    private static final boolean IS_MAC = OS_NAME.contains("mac");

    @BeforeEach
    public void setUp() throws InterruptedException {
        try {
            printEnvironmentInfo();

            this.browserType = detectBrowser();

            File htmlFile = findhtmlFile();
            String htmlUrl = determineHtmlUrl(htmlFile);

            webDriver = createWebDriver(this.browserType);

            wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            mockServer = ClientAndServer.startClientAndServer(8081);
            mockServerClient = new MockServerClient("localhost", 8081);

            mockServerClient.
                    when(HttpRequest.request().withMethod("OPTIONS").withPath(".*"))
                    .respond(HttpResponse.response()
                            .withHeader("Access-Control-Allow-Origin", "*")
                            .withHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                            .withHeader("Access-Control-Allow-Headers",
                                    "Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Methods, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With"));

            System.out.println("\n=== NAVIGATING TO PAGE ===");
            System.out.println("Navigate to: " + htmlUrl);
            webDriver.get(htmlUrl);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            System.out.println("Page Loaded Successfully");

            printPageInfo();

            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("\n=== SETUP FAILED ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

            cleanup();
            throw new RuntimeException("Setup Failed", e);
        }
    }

    private void printEnvironmentInfo() {
        System.out.println("=== ENVIRONMENT INFO ===");
        System.out.println("OS: " + OS_NAME + " (" + OS_ARCH + ")");
        System.out.println("Architecture: " + (IS_ARM ? "ARM64" : "x86/x64"));
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
    }

    private String detectBrowser() {
        System.out.println("\n=== BROWSER DETECTION ===");

        if (findChromeBinary() != null) {
            return "chrome";
        } else if (findEdgeBinary() != null) {
            return "edge";
        }

        throw new RuntimeException("No Compatible Browser Driver Found");
    }

    private BrowserConfig checkProjectDriverFolder() {
        File driverFolder = new File("driver");
        if (!driverFolder.exists() || !driverFolder.isDirectory()) {
            System.out.println("No Driver Folder Found in Project Root");

            return null;
        }

        System.out.println("Found Driver Folder, Checking for Executables...");

        String[] edgeDriverNames = IS_WINDOWS ? new String[]{"msedgedriver.exe", "edgedriver.exe"} : new String[]{"msedgedriver", "edgedriver"};

        for (String driverName : edgeDriverNames) {
            File driverFile = new File(driverFolder, driverName);
            if (driverFile.exists()) {
                makeExecutable(driverFile);
                if (driverFile.canExecute()) {
                    System.out.println("Found Edge Executable: " + driverFile.getAbsolutePath());

                    return new BrowserConfig("edge", driverFile.getAbsolutePath(), findEdgeBinary());
                }
            }
        }

        String[] chromeDriverNames = IS_WINDOWS ? new String[]{"chromedriver.exe"} : new String[]{"chromedriver"};

        for (String driverName : chromeDriverNames) {
            File driverFile = new File(driverFolder, driverName);
            if (driverFile.exists()) {
                makeExecutable(driverFile);
                if (driverFile.canExecute()) {
                    System.out.println("Found Chrome driver: " + driverFile.getAbsolutePath());
                    return new BrowserConfig("chrome", driverFile.getAbsolutePath(), findChromeBinary());
                }
            }
        }

        System.out.println("No Compatible Executables Found in Driver Folder");
        return null;
    }

    private BrowserConfig checkSystemDrivers() {
        System.out.println("Checking System Drivers...");

        String[] chromeDriverPaths = {
                "/usr/bin/chromedriver",
                "/usr/local/bin/chromedriver",
                "/snap/bin/chromedriver",
                System.getProperty("user.home") + "/.cache/selenium/chromedriver/linux64/chromedriver",
                "/opt/chromedriver/chromedriver"
        };

        if (IS_WINDOWS) {
            chromeDriverPaths = new String[]{
                    "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe",
                    "C:\\ChromeDriver\\chromedriver.exe",
                    "chromedriver.exe"
            };
        }

        for (String driverPath : chromeDriverPaths) {
            File driverFile = new File(driverPath);
            if (driverFile.exists() && driverFile.canExecute()) {
                System.out.println("Found System Chrome Driver: " + driverPath);

                return new BrowserConfig("chrome", driverPath, findChromeBinary());
            }
        }

        if (IS_WINDOWS) {
            String[] edgeDriverPaths = {
                    "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedgedriver.exe",
                    "msedgedriver.exe"
            };

            for (String driverPath : edgeDriverPaths) {
                File driverFile = new File(driverPath);
                if (driverFile.exists() && driverFile.canExecute()) {
                    System.out.println("Found system Edge driver: " + driverPath);
                    return new BrowserConfig("edge", driverPath, findEdgeBinary());
                }
            }
        }

        return null;
    }

    private String findChromeBinary() {
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

    private String findEdgeBinary() {
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

    private void makeExecutable(File file) {
        if (!file.canExecute()) {
            try {
                file.setExecutable(true);
                System.out.println("Made Executable: " + file.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Error Making Executable: " + e.getMessage());
            }
        }
    }

    private File findhtmlFile() {
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

    private String determineHtmlUrl(File htmlFile) {
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

    private boolean isPython3Available() {
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

    private String startHttpServer(File htmlFile) throws Exception {
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

    private WebDriver createWebDriver(String browserType) {
        System.out.println("\n=== CREATING WEBDRIVER ===");
        System.out.println("Browser: " + browserType);

        if ("edge".equals(browserType)) {
            WebDriverManager.edgedriver().setup();
            return createEdgeDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            return createChromeDriver();
        }
    }

    private WebDriver createEdgeDriver() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments(getEdgeArguments());

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        edgeOptions.setCapability("ms:loggingPrefs", loggingPreferences);

        return new EdgeDriver(edgeOptions);
    }

    private WebDriver createChromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(getChromeArguments());

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        chromeOptions.setCapability("goog:loggingPrefs", loggingPreferences);

        return new ChromeDriver(chromeOptions);
    }

    private String[] getEdgeArguments() {
        return getCommonBrowserArguments();
    }

    private String[] getChromeArguments() {
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

    private void printPageInfo() {
        System.out.println("Page Title: " + webDriver.getTitle());
        System.out.println("Current URL: " + webDriver.getCurrentUrl());
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
                System.err.println("Error Quitting WebDriver: " + e.getMessage());
            }
        }

        if (mockServer != null) {
            mockServer.stop();
        }

        if (mockServerClient != null) {
            mockServerClient.close();
        }
    }

    @AfterEach
    public void tearDown() {
        System.out.println("\n=== TEARDOWN ===");
        cleanup();
        System.out.println("Teardown Complete");
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

    @Test
    public void validRegistrationTest() throws InterruptedException {
        WebElement nameInput = webDriver.findElement(By.id("username-input"));
        WebElement emailInput = webDriver.findElement(By.id("email-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement passwordRepeatInput = webDriver.findElement(By.id("repeat-password-input"));
        WebElement submitButton = webDriver.findElement(By.id("register-button"));

        mockServerClient
                .when(HttpRequest.request().withMethod("POST").withPath("/register"))
                .respond(HttpResponse.response()
                        .withStatusCode(201)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Access-Control-Allow-Origin", "*"));

        nameInput.sendKeys("correct");
        emailInput.sendKeys("correct@example.com");
        passwordInput.sendKeys("correct");
        passwordRepeatInput.sendKeys("correct");
        submitButton.click();

        Thread.sleep(1000);
        Assertions.assertTrue(webDriver.getCurrentUrl().contains("login"));
    }

    @Test
    public void failedRegistrationTest() throws InterruptedException {
        WebElement nameInput = webDriver.findElement(By.id("username-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement passwordRepeatInput = webDriver.findElement(By.id("repeat-password-input"));
        WebElement submitButton = webDriver.findElement(By.id("register-button"));

        mockServerClient
                .when(HttpRequest.request().withMethod("POST").withPath("/register"))
                .respond(HttpResponse.response()
                        .withStatusCode(409)
                        .withHeader("Content-Type", "application/json")
                        .withHeader("Access-Control-Allow-Origin", "*"));

        nameInput.sendKeys("duplicate");
        passwordInput.sendKeys("testpass");
        passwordRepeatInput.sendKeys("testpass");
        submitButton.click();

        wait.until(ExpectedConditions.alertIsPresent());
        webDriver.switchTo().alert().dismiss();
        Thread.sleep(1000);

        Assertions.assertTrue(webDriver.getCurrentUrl().contains("register"));
    }

    @Test
    public void invalidRegistrationTest() throws InterruptedException {
        WebElement nameInput = webDriver.findElement(By.id("username-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement passwordRepeatInput = webDriver.findElement(By.id("repeat-password-input"));
        WebElement submitButton = webDriver.findElement(By.id("register-button"));

        nameInput.sendKeys("testuser");
        passwordInput.sendKeys("password123");
        passwordRepeatInput.sendKeys("mismatch");
        submitButton.click();

        wait.until(ExpectedConditions.alertIsPresent());
        webDriver.switchTo().alert().dismiss();
        Thread.sleep(1000);

        Assertions.assertTrue(webDriver.getCurrentUrl().contains("register"));
    }
}
