package com.revature.projects.socialMediaAppV2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserRegistrationTest {
    ApplicationContext applicationContext;
    HttpClient httpClient;
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws InterruptedException {
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[] {};
        applicationContext = SpringApplication.run(SocialMediaApp.class, args);
        Thread.sleep(500);
    }

    @AfterEach
    public void teardown() throws InterruptedException {
        Thread.sleep(500);
        SpringApplication.exit(applicationContext);
    }

    @Test
    public void registerUserSuccessful() throws IOException, InterruptedException {
        String json = "{\"username\":\"user\",\"password\":\"password\"}";
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Status Code: " + status);
    }

    @Test
    public void registerUserDuplicateUsername() throws IOException, InterruptedException {
        String json = "{\"username\":\"user\",\"password\":\"password\"}";
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response1 = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status1 = response1.statusCode();
        int status2 = response2.statusCode();

        Assertions.assertEquals(200, status1, "Expected Status Code 200 - Actual Status Code: " + status1);
        Assertions.assertEquals(409, status2, "Expected Status Code 409 - Actual Status Code: " + status2);
    }
}
