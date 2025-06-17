package com.tutorial.loops;

public class WhileLoops {
    public static void main(String[] args) {
        System.out.println("=== While Loops ===");

        int number = 5;
        int multiplier = 1;

        while (multiplier <= 10) {
            System.out.printf("%d * %d = %d%n", number, multiplier, number * multiplier);
            multiplier++;
        }
    }
}
