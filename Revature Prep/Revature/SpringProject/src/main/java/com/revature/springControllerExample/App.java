package com.revature.springControllerExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("=== Spring MVC App ===");

        SpringApplication.run(App.class, args);
    }
}
