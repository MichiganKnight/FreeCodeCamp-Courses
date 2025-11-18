package com.revature.weekOne;

public class QuizHelp {
    public static void main(String[] args) {
        System.out.println("=== Week 1 Exercises ===");

        pizzaFlow();
        loopOutput();
        iterationHelp();
        simpleOutput();
    }

    private static void pizzaFlow() {
        String food = "Pizza";

        if (food == "pizza") {
            System.out.println("Cheese Pizza");
        } else if (food.equals("pizza")) {
            System.out.println("Veggie Pizza");
        } else if (food.equals("pizza") || food.equals("Pizza")) {
            System.out.println("Pepperoni Pizza");
        } else {
            System.out.println("You ordered a pizza?");
        }
    }

    private static void loopOutput() {
        for (int i = 0; i < 10; ++i) {
            System.out.print(i++ + ",");

            i++;
        }
    }

    private static void iterationHelp() {
        int a = 1;
        System.out.println(2*a++ + 5 + a);
        System.out.println(a++ + ++a + ++a);
    }

    private static void simpleOutput() {
        for (byte i = 0; i < 5; i ++) {
            System.out.print(i);
        }
    }
}
