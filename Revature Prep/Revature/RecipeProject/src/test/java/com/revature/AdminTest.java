package com.revature;

import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.revature.Utils.TestSetup.*;

public class AdminTest {
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
    public void noAdminNoLinkTest() throws InterruptedException {
        webDriver.get("http://localhost:8083/login/login-page.html");

        try {
            WebDriverWait shortWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
            Alert alert = shortWait.until(ExpectedConditions.alertIsPresent());

            System.out.println("Dismissing Leftover Alert: " + alert.getText());
            alert.dismiss();
        } catch (Exception ignored) {}

        WebElement usernameInput = webDriver.findElement(By.id("login-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys("JoeCool");
        passwordInput.sendKeys("redbarron");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("recipe-page"));
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return window.sessionStorage.getItem('is-admin') !== null;"));

        String isAdmin = (String) js.executeScript("return window.sessionStorage.getItem('is-admin');");
        Assertions.assertEquals("false", isAdmin);

        WebElement adminLink = webDriver.findElement(By.id("admin-link"));
        Assertions.assertFalse(adminLink.isDisplayed());

        performLogout();
    }

    @Test
    public void adminLinkTest() throws InterruptedException {
        webDriver.get("http://localhost:8083/login/login-page.html");

        WebElement usernameInput = webDriver.findElement(By.id("login-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys("ChefTrevin");
        passwordInput.sendKeys("trevature");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("recipe-page"));
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return window.sessionStorage.getItem('is-admin') !== null;"));

        String isAdmin = (String) js.executeScript("return window.sessionStorage.getItem('is-admin');");
        Assertions.assertEquals("true", isAdmin);

        WebElement adminLink = webDriver.findElement(By.id("admin-link"));
        Assertions.assertTrue(adminLink.isDisplayed());

        performLogout();
    }

    @Test
    public void displayIngredientsOnInitTest() throws InterruptedException {
        webDriver.get("http://localhost:8083/login/login-page.html");

        WebElement usernameInput = webDriver.findElement(By.id("login-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys("ChefTrevin");
        passwordInput.sendKeys("trevature");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("recipe-page"));
        Thread.sleep(1000);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("admin-link")));
        WebElement adminLink = webDriver.findElement(By.id("admin-link"));
        adminLink.click();

        Thread.sleep(1000);

        WebElement list = webDriver.findElement(By.id("ingredient-list"));
        String innerHTML = list.getAttribute("innerHTML");

        Assertions.assertTrue(innerHTML.contains("carrot"));
        Assertions.assertTrue(innerHTML.contains("potato"));
        Assertions.assertTrue(innerHTML.contains("tomato"));
        Assertions.assertTrue(innerHTML.contains("lemon"));
        Assertions.assertTrue(innerHTML.contains("rice"));
        Assertions.assertTrue(innerHTML.contains("stone"));

        webDriver.findElement(By.id("back-link")).click();
        Thread.sleep(1000);

        performLogout();
    }

    @Test
    public void addIngredientPostTest() throws InterruptedException {
        webDriver.get("http://localhost:8083/login/login-page.html");

        WebElement usernameInput = webDriver.findElement(By.id("login-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys("ChefTrevin");
        passwordInput.sendKeys("trevature");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("recipe-page"));
        Thread.sleep(1000);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("admin-link")));
        WebElement adminLink = webDriver.findElement(By.id("admin-link"));
        adminLink.click();

        Thread.sleep(1000);

        WebElement nameInput = webDriver.findElement(By.id("add-ingredient-name-input"));
        WebElement ingredientSubmit = webDriver.findElement(By.id("add-ingredient-submit-button"));

        nameInput.sendKeys("salt");
        ingredientSubmit.click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ingredient-list")));
        String innerHTML = webDriver.findElement(By.id("ingredient-list")).getAttribute("innerHTML");
        Assertions.assertTrue(innerHTML.contains("salt"), "Expected Ingredient to be Added");

        webDriver.findElement(By.id("back-link")).click();
        Thread.sleep(1000);

        performLogout();
    }

    @Test
    public void deleteIngredientDeleteTest() throws InterruptedException {
        webDriver.get("http://localhost:8083/login/login-page.html");

        try {
            WebDriverWait shortWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
            Alert alert = shortWait.until(ExpectedConditions.alertIsPresent());

            System.out.println("Dismissing Leftover Alert: " + alert.getText());
            alert.dismiss();
        } catch (Exception ignored) {}

        WebElement usernameInput = webDriver.findElement(By.id("login-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys("ChefTrevin");
        passwordInput.sendKeys("trevature");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("recipe-page"));
        Thread.sleep(1000);

        webDriver.findElement(By.id("admin-link")).click();
        Thread.sleep(1000);

        WebElement nameInput = webDriver.findElement(By.id("delete-ingredient-name-input"));
        WebElement ingredientSubmitButton = webDriver.findElement(By.id("delete-ingredient-submit-button"));

        nameInput.sendKeys("tomato");
        ingredientSubmitButton.click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ingredient-list")));
        String innerHTML = webDriver.findElement(By.id("ingredient-list")).getAttribute("innerHTML");
        Assertions.assertFalse(innerHTML.contains("tomato"), "Expected Ingredient to be Deleted");

        webDriver.findElement(By.id("back-link")).click();
        Thread.sleep(1000);

        performLogout();
    }
}
