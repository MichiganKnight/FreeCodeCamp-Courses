package com.revature.Connection;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static com.revature.Utils.TestSetup.*;

public class RegisterTest {
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() throws InterruptedException {
        try {
            printEnvironmentInfo();

            detectBrowser();

            File htmlFile = findhtmlFile("register");
            String htmlUrl = determineHtmlUrl(htmlFile);

            WebDriver webDriver = getWebDriver();

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

    private WebDriver getWebDriver() {
        return createWebDriver(detectBrowser());
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        System.out.println("\n=== TEARDOWN ===");
        cleanup();
        System.out.println("Teardown Complete");
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
