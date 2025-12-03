package com.revature.weekTwo.encapsulation;

public class EmployeeCount {
    private int numberOfEmployees = 0;

    public void setNumberOfEmployees(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Employee Count Cannot be Negative");
        }

        numberOfEmployees = count;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }
}
