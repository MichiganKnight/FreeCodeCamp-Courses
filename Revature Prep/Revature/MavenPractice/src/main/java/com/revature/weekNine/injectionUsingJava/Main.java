package com.revature.weekNine.injectionUsingJava;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Bean Configuration with Java ===");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EmployeeConfig.class);

        String[] allBeanNames = context.getBeanDefinitionNames();
        System.out.println("\nBeans Created Involving Java-Based Config:");
        for (String beanName : allBeanNames) {
            System.out.println(beanName);
        }

        context.close();
    }
}
