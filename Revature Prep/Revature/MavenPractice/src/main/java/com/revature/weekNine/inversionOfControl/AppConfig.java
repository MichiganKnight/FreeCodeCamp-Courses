package com.revature.weekNine.inversionOfControl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.revature.weekNine.inversionOfControl")
public class AppConfig {
    @Bean
    public MyDependency myDependency() {
        return new MyDependencyImpl();
    }
}
