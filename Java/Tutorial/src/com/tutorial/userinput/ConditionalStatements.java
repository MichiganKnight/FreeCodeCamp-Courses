package com.tutorial.userinput;

import java.util.Scanner;

public class ConditionalStatements {
    public static void main(String[] args) {
        System.out.println("=== Conditional Statements ===");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter First Number: ");
        double number1 = scanner.nextDouble();

        System.out.print("Enter Second Number: ");
        double number2 = scanner.nextDouble();

        scanner.nextLine();

        //System.out.printf("Number 1: %f%nNumber 2: %f%n", number1, number2);
        System.out.print("What Operation Would You Like To Perform? ");
        String operation = scanner.nextLine();

        if (operation.equalsIgnoreCase("sum") || operation.equalsIgnoreCase("+") || operation.equalsIgnoreCase("add")) {
            System.out.printf("%f + %f = %f%n", number1, number2, number1 + number2);
        } else if (operation.equalsIgnoreCase("difference") || operation.equalsIgnoreCase("-") || operation.equalsIgnoreCase("sub") || operation.equalsIgnoreCase("subtract")) {
            System.out.printf("%f - %f = %f%n", number1, number2, number1 - number2);
        } else if (operation.equalsIgnoreCase("product") || operation.equalsIgnoreCase("*") || operation.equalsIgnoreCase("mult") || operation.equalsIgnoreCase("multiply")) {
            System.out.printf("%f * %f = %f%n", number1, number2, number1 * number2);
        } else if (operation.equalsIgnoreCase("quotient") || operation.equalsIgnoreCase("/") || operation.equalsIgnoreCase("div") || operation.equalsIgnoreCase("divide")) {
            if (number2 == 0) {
                System.out.println("Cannot Divide By Zero!");
            } else {
                System.out.printf("%f / %f = %f%n", number1, number2, number1 / number2);
            }
        } else {
            System.out.println("Invalid Operation!%n");
        }

        scanner.close();
    }
}
