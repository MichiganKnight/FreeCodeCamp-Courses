package com.revature.projects.SocialMediaBlogAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.Util.ConnectionUtil;
import com.revature.projects.socialMediaBlogAPI.Controller.SocialMediaController;
import com.revature.projects.socialMediaBlogAPI.Model.Account;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserLoginTest {
    SocialMediaController socialMediaController;
    HttpClient webClient;
    ObjectMapper objectMapper;
    Javalin app;

    @BeforeEach
    public void setUp() throws InterruptedException {
        ConnectionUtil.resetTestDatabase();
        socialMediaController = new SocialMediaController();
        app = socialMediaController.startAPI();
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();

        app.start(8080);
        Thread.sleep(1000);
    }

    @AfterEach
    public void tearDown() {
        app.stop();
    }

    @Test
    public void loginSuccessful() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"testuser1\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assertions.assertEquals(200, status);
        ObjectMapper om = new ObjectMapper();
        Account expectedAccount = new Account(1, "testuser1", "password");
        Account actualAccount = om.readValue(response.body().toString(), Account.class);
        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    public void loginInvalidUsername() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"testuser404\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assertions.assertEquals(401, status);
        Assertions.assertEquals("", response.body().toString());
    }

    @Test
    public void loginInvalidPassword() throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/login"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"testuser1\", " +
                        "\"password\": \"pass123\" }"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        Assertions.assertEquals(401, status);
        Assertions.assertEquals("", response.body().toString());
    }
}
