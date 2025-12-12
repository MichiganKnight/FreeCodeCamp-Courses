package com.revature.projects.SocialMediaBlogAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.Util.ConnectionUtil;
import com.revature.projects.socialMediaBlogAPI.Controller.SocialMediaController;
import com.revature.projects.socialMediaBlogAPI.Model.Message;
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

public class UpdateMessageTextTest {
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
    public void updateMessageSuccessful() throws IOException, InterruptedException {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/1"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(
                        "{\"message_text\": \"updated message\" }"
                ))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status);

        ObjectMapper om = new ObjectMapper();
        Message expectedMessage = new Message(1, 1, "updated message", 1669947792);
        Message actualMessage = om.readValue(response.body().toString(), Message.class);
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void updateMessageMessageNotFound() throws IOException, InterruptedException {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/2"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(
                        "{\"message_text\": \"updated message\" }"
                ))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status);

        Assertions.assertTrue(response.body().toString().isEmpty());
    }

    @Test
    public void updateMessageMessageStringEmpty() throws IOException, InterruptedException {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/2"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(
                        "{\"message_text\": \"\" }"
                ))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status);

        Assertions.assertTrue(response.body().toString().isEmpty());
    }

    @Test
    public void updateMessageMessageTooLong() throws IOException, InterruptedException {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/2"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(
                        "{\"message_text\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\" }"
                ))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status);

        Assertions.assertTrue(response.body().toString().isEmpty());
    }
}
