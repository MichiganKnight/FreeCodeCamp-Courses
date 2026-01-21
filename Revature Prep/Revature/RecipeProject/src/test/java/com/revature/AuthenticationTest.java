package com.revature;

import io.javalin.Javalin;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.revature.Utils.TestSetup.*;

public class AuthenticationTest {
    private static WebDriverWait wait;
    private static Javalin app;
    private static JavascriptExecutor js;

    private static Javalin server;
    private static final int PORT = 8083;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        try {
            printEnvironmentInfo();

            int port = 8081;
            app = Main.startServer(port, true);

            System.out.println("Starting Local Static Server for Frontend Files");
            server = Javalin.create(config -> {
                config.staticFiles.add("/public/frontend");
            }).start(PORT);
            System.out.println("Frontend Started on Port: " + PORT);

            WebDriver webDriver = getWebDriver();

            wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            js = (JavascriptExecutor) webDriver;

            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("\n=== SETUP FAILED ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

            cleanup();
            throw new RuntimeException("Setup Failed", e);
        }
    }

    private static WebDriver getWebDriver() {
        return createWebDriver(detectBrowser());
    }

    @AfterAll
    public static void tearDown() throws InterruptedException {
        System.out.println("\n=== TEARDOWN ===");
        try {
            if (app != null) {
                app.stop();
                System.out.println("Backend Server Stopped Successfully");
            }

            if (server != null) {
                server.stop();
                System.out.println("Frontend Server Stopped Successfully");
            }
        } catch (Exception e) {
            System.err.println("Error Stopping Server: " + e.getMessage());
        }

        cleanup();
        System.out.println("Teardown Complete");
    }

    @AfterEach
    public void tearDownBetween() {
        performLogout();
    }

    @AfterAll
    public static void stopServer() {
        try {
            if (server != null) {
                server.stop();
                System.out.println("Javalin Server Stopped Successfully");
            }
        } catch (Exception e) {
            System.err.println("Error Stopping Javalin Server: " + e.getMessage());
        }
    }

    private static void performLogout() {
        WebElement logoutButton = webDriver.findElement(By.id("logout-button"));
        logoutButton.click();
    }

    @Test
    public void authTest1() {
        webDriver.get("http://localhost:8083/login/login-page.html");

        WebElement usernameInput = webDriver.findElement(By.id("login-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys("ChefTrevin");
        passwordInput.sendKeys("trevature");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("recipe-page"));

        Object authToken = js.executeScript("return window.sessionStorage.getItem('auth-token');");
        Assertions.assertNotNull(authToken, "'auth-token' Should not be null");

        WebElement nameInput = webDriver.findElement(By.id("delete-recipe-name-input"));
        WebElement deleteButton = webDriver.findElement(By.id("delete-recipe-submit-input"));

        nameInput.sendKeys("carrot soup");
        deleteButton.click();

        boolean alert = isAlertPresent(webDriver);
        Assertions.assertFalse(alert, "Admin User Should not be Trigger Alert on Delete");
    }

    @Test
    public void authTest2() {
        webDriver.get("http://localhost:8083/login/login-page.html");

        try {
            WebDriverWait shortWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
            Alert preAlert = shortWait.until(ExpectedConditions.alertIsPresent());

            System.out.println("Alert Present Before Login: " + preAlert.getText());
            preAlert.dismiss();
        } catch (Exception ignored) {
            System.out.println("No Alert Present Before Login - Proceeding...");
        }

        WebElement usernameInput = webDriver.findElement(By.id("login-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys("JoeCool");
        passwordInput.sendKeys("redbarron");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("recipe-page"));

        Object authToken = js.executeScript("return window.sessionStorage.getItem('auth-token');");
        Assertions.assertNotNull(authToken, "'auth-token' Should not be null");

        WebElement nameInput = webDriver.findElement(By.id("delete-recipe-name-input"));
        WebElement deleteButton = webDriver.findElement(By.id("delete-recipe-submit-input"));

        nameInput.sendKeys("stone soup");
        deleteButton.click();

        Alert postAlert = wait.until(ExpectedConditions.alertIsPresent());
        boolean isAlertVisible = isAlertPresent(webDriver);

        postAlert.dismiss();
        Assertions.assertTrue(isAlertVisible, "Non-Admin User Should Trigger Alert on Delete");
    }

    public static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
