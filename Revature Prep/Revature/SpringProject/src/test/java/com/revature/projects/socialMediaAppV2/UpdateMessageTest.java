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

public class UpdateMessageTest {
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
    public void updateMessageSuccessful() throws IOException, InterruptedException {
        String json = "{\"messageText\": \"text changed\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/9999"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Status Code: " + status);
        Integer actualResult = objectMapper.readValue(response.body().toString(), Integer.class);
        Assertions.assertTrue(actualResult.equals(1), "Expected to Modify 1 Row - Actual Rows Modified: " + actualResult + " Rows");
    }

    @Test
    public void updateMessageMessageNotFound() throws IOException, InterruptedException {
        String json = "{\"messageText\": \"text changed\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/5050"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Status Code: " + status);
        System.out.println(response.body());
    }

    @Test
    public void updateMessageMessageStringEmpty() throws IOException, InterruptedException {
        String json = "{\"messageText\": \"\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/9999"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Status Code: " + status);
    }

    @Test
    public void updateMessageMessageTooLong() throws IOException, InterruptedException {
        String json = "{\"messageText\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/9999"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Status Code: " + status);
    }
}
