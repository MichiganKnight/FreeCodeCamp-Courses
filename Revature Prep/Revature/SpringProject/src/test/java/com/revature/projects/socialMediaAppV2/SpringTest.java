package com.revature.projects.socialMediaAppV2;

import com.revature.projects.socialMediaAppV2.Controller.SocialMediaController;
import com.revature.projects.socialMediaAppV2.Entity.Account;
import com.revature.projects.socialMediaAppV2.Entity.Message;
import com.revature.projects.socialMediaAppV2.Repository.AccountRepository;
import com.revature.projects.socialMediaAppV2.Repository.MessageRepository;
import com.revature.projects.socialMediaAppV2.Service.AccountService;
import com.revature.projects.socialMediaAppV2.Service.MessageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class SpringTest {
    ApplicationContext applicationContext;

    @BeforeEach
    public void setup() {
        String[] args = new String[] {};
        applicationContext = SpringApplication.run(SocialMediaApp.class, args);
    }

    @AfterEach
    public void teardown() {
        SpringApplication.exit(applicationContext);
    }

    @Test
    public void getSocialMediaControllerBean() {
        SocialMediaController bean = applicationContext.getBean(SocialMediaController.class);
        Assertions.assertNotNull(bean);
    }

    @Test
    public void getAccountServiceBean() {
        AccountService bean = applicationContext.getBean(AccountService.class);
        Assertions.assertNotNull(bean);
    }

    @Test
    public void getMessageServiceBean() {
        MessageService bean = applicationContext.getBean(MessageService.class);
        Assertions.assertNotNull(bean);
    }

    @Test
    public void getAccountRepositoryBean() {
        AccountRepository bean = applicationContext.getBean(AccountRepository.class);
        Assertions.assertNotNull(bean);
    }

    @Test
    public void getMessageRepositoryBean() {
        MessageRepository bean = applicationContext.getBean(MessageRepository.class);
        Assertions.assertNotNull(bean);
    }

    @Test
    public void accountRepositoryIsRepositoryTest() throws ReflectiveOperationException {
        AccountRepository repository = applicationContext.getBean(AccountRepository.class);
        Method[] repositoryMethods = repository.getClass().getMethods();
        Method saveMethod = null;
        Method findAllMethod = null;
        String expectedUsername = "ted";
        String expectedPassword = "password123";
        Account testAccount = new Account(expectedUsername, expectedPassword);

        for (Method m : repositoryMethods) {
            System.out.println(m.getName());

            if (m.getName().equals("save") && m.getParameterCount() == 1) {
                saveMethod = m;
            } else if (m.getName().equals("findAll") && m.getParameterCount() == 0) {
                findAllMethod = m;
            }
        }

        if (saveMethod == null || findAllMethod == null) {
            Assertions.fail("Save / FindAll Methods Not Found | Ensure AccountRepository Extends JPARepository");
        }

        List<Account> accountList1 = (List<Account>) findAllMethod.invoke(repository, new Object[] {});
        System.out.println(accountList1);
        Assertions.assertTrue(accountList1.size() == 4, "There Should Be No Account in the JPARepository on Startup");
        Account actualAccount = (Account) saveMethod.invoke(repository, testAccount);
        Assertions.assertEquals(actualAccount.getUsername(), expectedUsername);

        List<Account> accountList2 = (List<Account>) findAllMethod.invoke(repository, new Object[] {});
        Assertions.assertTrue(accountList2.size() > 4, "There Should Be Addable to the JPARepository");
    }

    @Test
    public void messageRepositoryIsRepositoryTest() throws ReflectiveOperationException {
        MessageRepository repository = applicationContext.getBean(MessageRepository.class);
        Method[] repositoryMethods = repository.getClass().getMethods();
        Method saveMethod = null;
        Method findAllMethod = null;
        int expectedPostedBy = 9999;
        String expectedText = "ted test 1";
        long expectedTimePostedEpoch = 999999999999L;
        Message testMessage = new Message(expectedPostedBy, expectedText, expectedTimePostedEpoch);

        for (Method m : repositoryMethods) {
            System.out.println(m.getName());

            if (m.getName().equals("save") && m.getParameterCount() == 1) {
                saveMethod = m;
            } else if (m.getName().equals("findAll") && m.getParameterCount() == 0) {
                findAllMethod = m;
            }
        }

        if (saveMethod == null || findAllMethod == null) {
            Assertions.fail("Save / FindAll Methods Not Found | Ensure MessageRepository Extends JPARepository");
        }

        List<Account> accountList1 = (List<Account>) findAllMethod.invoke(repository, new Object[] {});
        System.out.println(accountList1);
        Assertions.assertTrue(accountList1.size() == 3, "There Should Be No Messages in the JPARepository on Startup");
        Message actualMessage = (Message) saveMethod.invoke(repository, testMessage);
        Assertions.assertEquals(actualMessage.getMessageText(), expectedText);

        List<Account> accountList2 = (List<Account>) findAllMethod.invoke(repository, new Object[] {});
        Assertions.assertTrue(accountList2.size() > 3, "There Should Be Addable to the JPARepository");
    }

    @Test
    public void default404Test() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        int random = (int) (Math.random() * 100000);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/arbitrary" + random))
                .build();

        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(404, status);

        String body = response.body().toString();
        Assertions.assertTrue(body.contains("timestamp"));
        Assertions.assertTrue(body.contains("status"));
        Assertions.assertTrue(body.contains("error"));
        Assertions.assertTrue(body.contains("path"));
    }
}
