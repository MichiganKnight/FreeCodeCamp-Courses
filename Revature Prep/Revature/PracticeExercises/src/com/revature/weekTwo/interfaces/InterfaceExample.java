package com.revature.weekTwo.interfaces;

public class InterfaceExample {
    public static void main(String[] args) {
        System.out.println("=== Interface Example ===");

        Swimmer beluga;
        beluga = new Whale();
        beluga.swim();
        beluga.dive();
    }
}
