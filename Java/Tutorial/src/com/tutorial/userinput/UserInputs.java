package com.tutorial.userinput;

import java.util.Scanner;

public class UserInputs {
    public static void main(String[] args) {
        System.out.println("=== User Inputs ===");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine();

        System.out.printf("Hello %s! How are you?%n", name);

        System.out.print("Enter Your Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Your GPA: ");
        double gpa = Double.parseDouble(scanner.nextLine());

        System.out.printf("You are %d years old! Your GPA is: %f. What programming language do you prefer? ", age, gpa);
        String language = scanner.nextLine();

        System.out.printf("%s is the best language!%n", language);

        scanner.close();
    }
}
