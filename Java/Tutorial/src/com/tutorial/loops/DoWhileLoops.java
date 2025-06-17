package com.tutorial.loops;

public class DoWhileLoops {
    public static void main(String[] args) {
        System.out.println("=== Do While Loops ===");

        int number = 5;
        int multiplier = 1;

        do {
            System.out.printf("%d * %d = %d%n", number, multiplier, number * multiplier);
            multiplier++;
        } while (multiplier <= 10);
    }
}
