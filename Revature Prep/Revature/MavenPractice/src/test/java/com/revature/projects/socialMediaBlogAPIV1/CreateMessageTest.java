package com.revature.projects.socialMediaBlogAPIV1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.Util.ConnectionUtil;
import com.revature.projects.socialMediaBlogAPIV1.Controller.SocialMediaController;
import com.revature.projects.socialMediaBlogAPIV1.Model.Message;
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

public class CreateMessageTest {
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
    public void createMessageSuccessful() throws IOException, InterruptedException {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString("{"+
                        "\"posted_by\":1, " +
                        "\"message_text\": \"hello message\", " +
                        "\"time_posted_epoch\": 1669947792}"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status);

        ObjectMapper om = new ObjectMapper();
        Message expectedMessage = new Message(2, 1, "hello message", 1669947792);
        System.out.println(response.body().toString());
        Message actualMessage = om.readValue(response.body().toString(), Message.class);
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void createMessageMessageTextBlank() throws IOException, InterruptedException {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString("{"+
                        "\"posted_by\":1, " +
                        "\"message_text\": \"\", " +
                        "\"time_posted_epoch\": 1669947792}"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status);

        Assertions.assertTrue(response.body().toString().isEmpty());
    }

    @Test
    public void createMessageMessageGreaterThan255() throws IOException, InterruptedException {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString("{"+
                        "\"posted_by\":1, " +
                        "\"message_text\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\", " +
                        "\"time_posted_epoch\": 1669947792}"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status);

        Assertions.assertTrue(response.body().toString().isEmpty());
    }

    @Test
    public void createMessageUserNotInDb() throws IOException, InterruptedException {
        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString("{"+
                        "\"posted_by\":3, " +
                        "\"message_text\": \"message test\", " +
                        "\"time_posted_epoch\": 1669947792}"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status);

        Assertions.assertTrue(response.body().toString().isEmpty());
    }
}
