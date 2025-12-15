package com.revature.weekNine.annotationBased;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationBasedApplication {
    public static void main(String[] args) {
        System.out.println("=== Annotation Based Application Beans ===");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.revature.weekNine");

        String[] allBeanNames = context.getBeanDefinitionNames();
        System.out.println("\nBeans Created Involving Annotation-Based Config:");
        for (String beanName : allBeanNames) {
            System.out.println(beanName);
        }

        context.close();
    }
}