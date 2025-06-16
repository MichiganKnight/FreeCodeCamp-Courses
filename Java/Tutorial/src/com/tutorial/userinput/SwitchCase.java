package com.tutorial.userinput;

import java.util.Scanner;

public class SwitchCase {
    public static void main(String[] args) {
        System.out.println("=== Switch Case ===");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter First Number: ");
        double number1 = scanner.nextDouble();

        System.out.print("Enter Second Number: ");
        double number2 = scanner.nextDouble();

        scanner.nextLine();

        System.out.print("What Operation Would You Like To Perform? ");
        String operation = scanner.nextLine();

        switch (operation) {
            case "sum":
            case "add":
            case "+":
                System.out.printf("%f + %f = %f%n", number1, number2, number1 + number2);
                break;
            case "difference":
            case "sub":
            case "-":
            case "subtract":
                System.out.printf("%f - %f = %f%n", number1, number2, number1 - number2);
                break;
            case "product":
            case "mult":
            case "*":
            case "multiply":
                System.out.printf("%f * %f = %f%n", number1, number2, number1 * number2);
                break;
            case "quotient":
            case "div":
            case "/":
            case "divide":
                if (number2 == 0) {
                    System.out.println("Cannot Divide By Zero!");
                } else {
                    System.out.printf("%f / %f = %f%n", number1, number2, number1 / number2);
                }
                break;
            default:
                System.out.println("Invalid Operation!%n");
                break;
        }
    }
}
