package com.tutorial.operators;

public class Operators {
    public static void main(String[] args) {
        System.out.println("=== Arithmetic Operators ===");

        basicArithmeticOperators();
        assignmentOperators();
        relationalOperators();
        logicalOperators();
        unityOperators();
    }

    private static void basicArithmeticOperators() {
        System.out.println("=== Basic Arithmetic Operators ===");

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

    private static void assignmentOperators() {
        System.out.println("\n=== Assignment Operators ===");

        int number = 12;

        number += 5;
        // Works For Any Assignment Operators

        System.out.println("Number: " + number);
    }

    private static void relationalOperators() {
        System.out.println("\n=== Relational Operators ===");

        int number1 = 12;
        int number2 = 15;

        // Equal To
        System.out.println(number1 == number2);

        // Not Equal To
        System.out.println(number1 != number2);

        // Greater Than
        System.out.println(number1 > number2);

        // Less Than
        System.out.println(number1 < number2);

        // Greater Than or Equal To
        System.out.println(number1 >= number2);

        // Less Than or Equal To
        System.out.println(number1 <= number2);
    }

    private static void logicalOperators() {
        System.out.println("\n=== Logical Operators ===");

        int age = 26;

        System.out.println(age >= 18 && age <= 40);

        boolean isStudent = false;
        boolean isLibraryMember = true;

        System.out.println(isStudent || isLibraryMember);

        System.out.println(!isStudent);

        System.out.println(!isStudent || isLibraryMember);
    }

    private static void unityOperators() {
        System.out.println("\n=== Unity Operators ===");

        int score = 0;
        int turns = 10;

        score++;
        turns--;

        System.out.println("Score: " + score);
        System.out.println("Turns: " + turns);

        int number = 55;

        System.out.println("Number: " + number++);
        System.out.println("Number: " + number);

        System.out.println("Number: " + number--);
        System.out.println("Number: " + number);

        System.out.println("Number: " + ++number);
        System.out.println("Number: " + --number);
    }
}
