package com.revature.projects.socialMediaAppV2;

import com.revature.projects.socialMediaAppV2.Entity.Account;
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

public class UserLoginTest {
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
    public void loginSuccessful() throws IOException, InterruptedException {
        String json = "{\"accountId\":0,\"username\":\"testuser1\",\"password\":\"password\"}";
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status);

        Account expectedAccount = new Account(9999, "testuser1", "password");
        Account actualAccount = objectMapper.readValue(response.body().toString(), Account.class);
        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void loginInvalidUsername() throws IOException, InterruptedException {
        String json = "{\"accountId\":9999,\"username\":\"testuser404\",\"password\":\"password\"}";
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(401, status, "Expected Status Code 401 - Actual Status Code: " + status);
    }

    @Test
    public void loginInvalidPassword() throws IOException, InterruptedException {
        String json = "{\"accountId\":9999,\"username\":\"testuser1\",\"password\":\"pass404\"}";
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(401, status, "Expected Status Code 401 - Actual Status Code: " + status);
    }
}
