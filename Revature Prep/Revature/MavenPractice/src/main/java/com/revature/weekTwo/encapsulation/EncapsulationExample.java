package com.revature.weekTwo.encapsulation;

public class EncapsulationExample {
    public static void main(String[] args) {
        System.out.println("=== Encapsulation Example ===");

        EmployeeCount employeeCount = new EmployeeCount();
        employeeCount.setNumberOfEmployees(500);
        System.out.println("Employee Count: " + employeeCount.getNumberOfEmployees());
    }
}
