package com.revature.weekNine.beanScopes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Bean Scopes ===");

        beanScopes("Singleton");
        beanScopes("Prototype");
    }

    private static void beanScopes(String beanScope) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println();

        switch (beanScope) {
            case "Singleton":
                System.out.println("=== Singleton Bean Scope ===");

                Singleton singleton1 = (Singleton) context.getBean("singleton");
                Singleton singleton2 = (Singleton) context.getBean("singleton");

                if (singleton1 == singleton2) {
                    System.out.println(singleton1);
                    System.out.println(singleton2);

                    System.out.println("Same Objects Created");
                }
                break;

            case "Prototype":
                System.out.println("=== Prototype Bean Scope ===");

                Prototype prototype1 = (Prototype) context.getBean("prototype");
                Prototype prototype2 = (Prototype) context.getBean("prototype");

                if (prototype1 != prototype2) {
                    System.out.println(prototype1);
                    System.out.println(prototype2);

                    System.out.println("Different Objects Created");
                }
            default:
                break;
        }
    }

    @Configuration
    static class AppConfig {
        @Bean(name = "singleton")
        public Singleton singleton() {
            return new Singleton();
        }

        @Bean(name = "prototype")
        @Scope(value = "prototype")
        public Prototype prototype() {
            return new Prototype();
        }
    }

    @Component
    static class Singleton {
        Singleton() {
            System.out.println("Singleton Created");
        }
    }

    @Component
    @Scope(value = "prototype")
    static class Prototype {
        Prototype() {
            System.out.println("Prototype Created");
        }

        void printMessage() {
            System.out.println("Prototype Message");
        }
    }
}
