package com.revature.weekNine.injectionUsingJava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfig {
    @Bean
    public EmployeeController employeeController(EmployeeService employeeService) {
        return new EmployeeController(employeeService);
    }

    @Bean
    public EmployeeService employeeService(EmployeeRepository employeeRepository) {
        EmployeeService bean = new EmployeeService();
        bean.setEmployeeRepository(employeeRepository);
        return bean;
    }

    @Bean
    public EmployeeRepository employeeRepository() {
        return new EmployeeRepository();
    }
}
