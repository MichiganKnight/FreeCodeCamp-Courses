package com.revature.weekNine.inversionOfControl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Inversion of Control ===");

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MyClass myClass = context.getBean("myClass", MyClass.class);

        myClass.getMyDependency().doSomething();
    }
}
