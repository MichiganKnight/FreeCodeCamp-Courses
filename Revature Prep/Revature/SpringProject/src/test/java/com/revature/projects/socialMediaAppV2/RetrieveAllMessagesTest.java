package com.revature.projects.socialMediaAppV2;

import com.revature.projects.socialMediaAppV2.Entity.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RetrieveAllMessagesTest {
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
    public void getAllMessagesMessagesAvailable() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Status Code: " + status);

        List<Message> expectedResult = new ArrayList<Message>();
        expectedResult.add(new Message(9996, 9996, "test message 3", 1669947792L));
        expectedResult.add(new Message(9997, 9997, "test message 2", 1669947792L));
        expectedResult.add(new Message(9999, 9999, "test message 1", 1669947792L));
        List<Message> actualResult = objectMapper.readValue(response.body().toString(), new TypeReference<List<Message>>() {});
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
