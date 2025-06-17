package com.tutorial.loops;

public class ForLoops {
    public static void main(String[] args) {
        System.out.println("=== For Loops ===");

        basicForLoops();
        nestedForLoops();
        printOdds();
        inForLoop();
    }

    private static void basicForLoops() {
        System.out.println("=== Basic For Loops ===");

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int sum = 0;

        for (int index = 0; index < numbers.length; index++) {
            sum += numbers[index];

            System.out.println(numbers[index]);
        }

        System.out.println("Sum: " + sum);
    }

    private static void nestedForLoops() {
        System.out.println("\n=== Nested For Loops ===");

        // Outer loop to iterate through numbers 1-10
        for (int number = 1; number <= 12; number++) {
            // Inner loop to iterate through multipliers 1-10
            for (int multiplier = 1; multiplier <= 12; multiplier++) {
                // Print multiplication table entry in format: number * multiplier = result
                System.out.printf("%d * %d = %d%n", number, multiplier, number * multiplier);
            }
        }

        /*int number = 5;

        for (int multiplier = 1; multiplier < 10; multiplier++) {
            System.out.printf("%d * %d = %d%n", number, multiplier, number * multiplier);
        }*/
    }

    private static void printOdds() {
        System.out.println("\n=== Print Odds ===");

        for (int number = 1; number <= 50; number++) {
            if (number % 2 == 1) {
                System.out.println(number);
            }
        }
    }

    private static void inForLoop() {
        System.out.println("\n=== In For Loop ===");

        int[] numbers = {1, 2, 3, 4, 5};

        int sum = 0;

        for (int number : numbers) {
            System.out.println(number);

            sum += number;
        }

        System.out.println("Sum: " + sum);
    }
}