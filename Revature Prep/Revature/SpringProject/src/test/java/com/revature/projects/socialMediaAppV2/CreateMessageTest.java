package com.revature.projects.socialMediaAppV2;

import com.revature.projects.socialMediaAppV2.Entity.Message;
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

public class CreateMessageTest {
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
    public void createMessageSuccessful() throws IOException, InterruptedException {
        String json = "{\"postedBy\":9999,\"messageText\": \"hello message\",\"timePostedEpoch\": 1669947792}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Status Code: " + status);

        ObjectMapper objectMapper = new ObjectMapper();
        Message expectedResult = new Message(1, 9999, "hello message", Long.valueOf(1669947792));
        Message actualResult = objectMapper.readValue(response.body().toString(), Message.class);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createMessageMessageTextBlank() throws IOException, InterruptedException {
        String json = "{\"postedBy\":9999,\"messageText\": \"\",\"timePostedEpoch\": 1669947792}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Status Code: " + status);
    }

    @Test
    public void creatMessageMessageTextGreaterThan255() throws IOException, InterruptedException {
        String json = "{\"postedBy\":9999,"
                + "\"messageText\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\","
                + "\"timePostedEpoch\": 1669947792}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Status Code: " + status);
    }

    @Test
    public void createMessageUserNotInDb() throws IOException, InterruptedException {
        String json = "{\"postedBy\":5050,\"messageText\": \"hello message\",\"timePostedEpoch\": 1669947792}";
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Status Code: " + status);
    }
}
