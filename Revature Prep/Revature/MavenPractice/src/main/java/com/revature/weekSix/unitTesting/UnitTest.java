package com.revature.weekSix.unitTesting;

public class UnitTest {
    public static void main(String[] args) {
        System.out.println("=== JUnit Testing ===");

        isPrimeReturnsTrueWithPrimeInput(7);
        isPrimeReturnsFalseWithNonPrimeInput(9);
    }

    public static void isPrimeReturnsTrueWithPrimeInput(int number) {
        boolean actual = isPrime(number);
        boolean expected = true;

        if (actual == expected) {
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed!");
            System.out.println("Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static void isPrimeReturnsFalseWithNonPrimeInput(int number) {
        boolean actual = isPrime(number);
        boolean expected = false;

        if (actual == expected) {
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed!");
            System.out.println("Expected: " + expected + ", Actual: " + actual);
        }
    }

    public static boolean isPrime(int number) {
        if (number < 0) {
            return false;
        }

        for (int i = 2; i < Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
