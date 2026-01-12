/*package com.revature.weekTen.UserApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    //@Autowired
    //private ObjectMapper objectMapper;

    @Test
    public void createUserTest() {
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:8080", User.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}*/