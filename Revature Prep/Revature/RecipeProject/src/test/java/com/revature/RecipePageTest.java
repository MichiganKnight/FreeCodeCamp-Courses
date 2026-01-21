package com.revature;

import io.javalin.Javalin;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static com.revature.Utils.TestSetup.*;

public class RecipePageTest {
    private static Javalin app;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setUp() throws Exception {
        try {
            printEnvironmentInfo();

            int port = 8081;
            app = Main.startServer(port, true);

            File htmlFile = findhtmlFile("recipe");
            String htmlUrl = determineHtmlUrl(htmlFile);

            WebDriver webDriver = getWebDriver();

            wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            System.out.println("\n=== NAVIGATING TO PAGE ===");
            System.out.println("Navigating to: " + htmlUrl);
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

    private static WebDriver getWebDriver() {
        return createWebDriver(detectBrowser());
    }

    @AfterAll
    public static void tearDown() throws InterruptedException {
        System.out.println("\n=== TEARDOWN ===");
        cleanup();
        System.out.println("Teardown Complete");
    }

    @Test
    public void testH1RecipesExists() {
        List<WebElement> elements = webDriver.findElements(By.tagName("h1"));
        boolean flag = false;
        for (WebElement element : elements) {
            if (element.getText().toLowerCase().contains("recipes")) {
                flag = true;
                break;
            }
        }

        Assertions.assertTrue(flag);
    }

    @Test
    public void testUlExists() {
        WebElement element = webDriver.findElement(By.id("recipe-list"));
        Assertions.assertNotNull("ul", element.getTagName());
    }

    @Test
    public void testH2AddRecipeExists() {
        List<WebElement> elements = webDriver.findElements(By.tagName("h2"));
        boolean flag = false;
        for (WebElement element : elements) {
            if (element.getText().toLowerCase().contains("add a recipe")) {
                flag = true;
                break;
            }
        }

        Assertions.assertTrue(flag);
    }

    @Test
    public void testAddRecipeNameInputExists() {
        WebElement element = webDriver.findElement(By.id("add-recipe-name-input"));
        Assertions.assertEquals("input", element.getTagName());
    }

    @Test
    public void testAddRecipeInstructionsInputExists() {
        WebElement element = webDriver.findElement(By.id("add-recipe-instructions-input"));
        Assertions.assertEquals("textarea", element.getTagName());
    }

    @Test
    public void testAddRecipeSubmitButtonExists() {
        WebElement element = webDriver.findElement(By.id("add-recipe-submit-input"));
        Assertions.assertEquals("button", element.getTagName());
    }

    @Test
    public void testAddRecipeSubmitButtonTextNotEmpty() {
        WebElement element = webDriver.findElement(By.id("add-recipe-submit-input"));
        Assertions.assertTrue(element.getText().length() >= 1);
    }

    @Test
    public void testH2UpdateRecipeExists() {
        List<WebElement> elements = webDriver.findElements(By.tagName("h2"));
        boolean flag = false;
        for (WebElement element : elements) {
            if (element.getText().toLowerCase().contains("update a recipe")) {
                flag = true;
                break;
            }
        }

        Assertions.assertTrue(flag);
    }

    @Test
    public void testUpdateRecipeNameInputExists() {
        WebElement element = webDriver.findElement(By.id("update-recipe-name-input"));
        Assertions.assertEquals("input", element.getTagName());
    }

    @Test
    public void testUpdateRecipeInstructionsInputExists() {
        WebElement element = webDriver.findElement(By.id("update-recipe-instructions-input"));
        Assertions.assertEquals("textarea", element.getTagName());
    }

    @Test
    public void testUpdateRecipeSubmitButtonExists() {
        WebElement element = webDriver.findElement(By.id("update-recipe-submit-input"));
        Assertions.assertEquals("button", element.getTagName());
    }

    @Test
    public void testUpdateRecipeSubmitButtonTextNotEmpty() {
        WebElement element = webDriver.findElement(By.id("update-recipe-submit-input"));
        Assertions.assertTrue(element.getText().length() >= 1);
    }

    @Test
    public void testH2DeleteRecipeExists() {
        List<WebElement> elements = webDriver.findElements(By.tagName("h2"));
        boolean flag = false;
        for (WebElement element : elements) {
            if (element.getText().toLowerCase().contains("delete a recipe")) {
                flag = true;
                break;
            }
        }

        Assertions.assertTrue(flag);
    }

    @Test
    public void testDeleteRecipeNameInputExists() {
        WebElement element = webDriver.findElement(By.id("delete-recipe-name-input"));
        Assertions.assertEquals("input", element.getTagName());
    }

    @Test
    public void testDeleteRecipeSubmitButtonExists() {
        WebElement element = webDriver.findElement(By.id("delete-recipe-submit-input"));
        Assertions.assertEquals("button", element.getTagName());
    }

    @Test
    public void testDeleteRecipeSubmitButtonTextNotEmpty() {
        WebElement element = webDriver.findElement(By.id("delete-recipe-submit-input"));
        Assertions.assertTrue(element.getText().length() >= 1);
    }

    @Test
    public void searchBarExistsTest() {
        WebElement searchInput = webDriver.findElement(By.id("search-input"));
        WebElement searchButton = webDriver.findElement(By.id("search-button"));

        Assertions.assertTrue(searchInput.getTagName().equals("input"));
        Assertions.assertTrue(searchButton.getTagName().equals("button"));
    }
}
