package com.revature.weekThree;

public class LSP {
    public static void main(String[] args) {
        System.out.println("=== Liskov Substitution Principle (LSP) ===");
    }

    static class Bird {

    }

    interface Flyable {
        public void fly();
    }

    static class Penguin extends Bird implements Flyable {
        public void fly() {}
    }
}
