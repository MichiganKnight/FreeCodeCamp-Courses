package com.tutorial.variables;

public class Variables {
    static int age = 26;

    public static void main(String[] args) {
        System.out.println("=== Variables ===");

        // Static Variables Only Work With Static Classes
        System.out.println("Age: " + age);

        char percentSign = '%';
        System.out.println("Percent Sign: " + percentSign);
    }
}
