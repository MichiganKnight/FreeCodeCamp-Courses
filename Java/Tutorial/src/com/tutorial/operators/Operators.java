package com.tutorial.operators;

public class Operators {
    public static void main(String[] args) {
        System.out.println("=== Arithmatic Operators ===");

        double number1 = 12;
        double number2 = 5;

        // Addition
        //System.out.println(number1 + " + " + number2 + " = " + (number1 + number2));
        System.out.println("Addition: " + (number1 + number2));

        // Subtraction
        System.out.println("Subtraction: " + (number1 - number2));

        // Multiplication
        System.out.println("Multiplication: " + (number1 * number2));

        // Division
        System.out.println("Division: " + (number1 / number2));

        // Modulus
        System.out.println("Modulus: " + (number1 % number2));
    }
}
