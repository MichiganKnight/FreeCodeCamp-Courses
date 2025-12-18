package com.revature.projects.SpringDatabaseAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("=== Spring Database API App ===");

        SpringApplication.run(Application.class, args);
    }
}
