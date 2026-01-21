package com.revature;

import io.javalin.Javalin;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.revature.Utils.TestSetup.*;
import static com.revature.Utils.TestSetup.webDriver;

public class RecipePersistenceTest {
    private static WebDriverWait wait;

    private static Javalin server;
    private static Javalin app;
    private static final int PORT = 8083;

    @BeforeAll
    public static void setUp() throws InterruptedException {
        try {
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

            System.out.println("WebDriver Created Successfully");

            Thread.sleep(1000);

            performLogin();
        } catch (Exception e) {
            System.err.println("\n=== SETUP FAILED ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

            cleanup();
            throw new RuntimeException("Setup Failed", e);
        }
    }

    private void handleUnexpectedAlerts(WebDriver webDriver) {
        try {
            WebDriverWait alertWait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert Detected: " + alert.getText());
            alert.dismiss();
        } catch (TimeoutException e) {
            System.out.println("No Unexpected Alerts Detected");
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

    private static void performLogin() {
        webDriver.get("http://localhost:8083/login/login-page.html");

        WebElement usernameInput = webDriver.findElement(By.id("login-input"));
        WebElement passwordInput = webDriver.findElement(By.id("password-input"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys("ChefTrevin");
        passwordInput.sendKeys("trevature");
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("recipe-page"));
    }

    private static void performLogout() {
        WebElement logoutButton = webDriver.findElement(By.id("logout-button"));
        logoutButton.click();
    }

    @Test
    public void displayRecipesOnInitTest() throws InterruptedException {
        handleUnexpectedAlerts(webDriver);

        webDriver.navigate().refresh();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("recipe-list")));
        WebElement recipeList = webDriver.findElement(By.id("recipe-list"));
        String innerHTML = recipeList.getAttribute("innerHTML");

        Assertions.assertTrue(innerHTML.contains("carrot soup"), "Expected Recipes to be Displayed");
        Assertions.assertTrue(innerHTML.contains("potato soup"), "Expected Recipes to be Displayed");
        Assertions.assertTrue(innerHTML.contains("tomato soup"), "Expected Recipes to be Displayed");
        Assertions.assertTrue(innerHTML.contains("lemon rice soup"), "Expected Recipes to be Displayed");
        Assertions.assertTrue(innerHTML.contains("stone soup"), "Expected Recipes to be Displayed");
    }

    @Test
    public void addRecipePostTest() {
        WebElement nameInput = webDriver.findElement(By.id("add-recipe-name-input"));
        WebElement instructionsInput = webDriver.findElement(By.id("add-recipe-instructions-input"));
        WebElement addButton = webDriver.findElement(By.id("add-recipe-submit-input"));

        nameInput.sendKeys("Beef Stroganoff");
        instructionsInput.sendKeys("Mix beef with sauce and serve over pasta");
        addButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("recipe-list")));
        WebElement recipeList = webDriver.findElement(By.id("recipe-list"));
        String innerHTML = recipeList.getAttribute("innerHTML");

        Assertions.assertTrue(innerHTML.contains("Beef Stroganoff"), "Expected Recipe to be Added");
    }

    @Test
    public void updateRecipePutTest() {
        WebElement nameInput = webDriver.findElement(By.id("update-recipe-name-input"));
        WebElement instructionsInput = webDriver.findElement(By.id("update-recipe-instructions-input"));
        WebElement updateButton = webDriver.findElement(By.id("update-recipe-submit-input"));

        nameInput.sendKeys("carrot soup");
        instructionsInput.sendKeys("Updated instructions for carrot soup");
        updateButton.click();

        handleUnexpectedAlerts(webDriver);

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("recipe-list"), "Updated instructions for carrot soup"));
        WebElement recipeList = webDriver.findElement(By.id("recipe-list"));
        String innerHTML = recipeList.getAttribute("innerHTML");

        Assertions.assertTrue(innerHTML.contains("Updated instructions for carrot soup"), "Expected Recipe to be Updated");
    }

    @Test
    public void deleteRecipeDeleteTest() {
        WebElement nameInput = webDriver.findElement(By.id("delete-recipe-name-input"));
        WebElement deleteButton = webDriver.findElement(By.id("delete-recipe-submit-input"));

        nameInput.sendKeys("stone soup");
        deleteButton.click();

        boolean recipeDeleted = wait.until(driver -> {
            WebElement recipeList = driver.findElement(By.id("recipe-list"));
            String innerHTML = recipeList.getAttribute("innerHTML");
            return !innerHTML.contains("stone soup");
        });

        Assertions.assertTrue(recipeDeleted, "Expected Recipe to be Deleted");
    }

    @Test
    public void searchFiltersTest() throws InterruptedException {
        WebElement searchInput = webDriver.findElement(By.id("search-input"));
        WebElement searchButton = webDriver.findElement(By.id("search-button"));
        WebElement recipeList = webDriver.findElement(By.id("recipe-list"));

        String searchTerm = "to soup";
        searchInput.sendKeys(searchTerm);
        searchButton.click();

        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("recipe-list")));
        String innerHTML = recipeList.getAttribute("innerHTML");

        Assertions.assertTrue(innerHTML.contains("potato soup"), "Expected Potato Soup to be Displayed");
        Assertions.assertTrue(innerHTML.contains("tomato soup"), "Expected Tomato Soup to not be Displayed");
        Assertions.assertFalse(innerHTML.contains("stone soup"), "Expected Stone to not be Displayed");
        Assertions.assertFalse(innerHTML.contains("carrot soup"), "Expected Carrot Soup to not be Displayed");
        Assertions.assertFalse(innerHTML.contains("lemon rice soup"), "Expected Lemon Rice Soup to not be Displayed");
    }
}
