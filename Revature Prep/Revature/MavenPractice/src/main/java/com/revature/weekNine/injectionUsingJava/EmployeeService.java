package com.revature.weekNine.injectionUsingJava;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
