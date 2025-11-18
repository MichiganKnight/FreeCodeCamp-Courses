package com.revature.weekTwo.abstraction;

public class BasicAbstraction {
    public static void main(String[] args) {
        System.out.println("=== Basic Abstraction ===");

        Shape circle = new Circle("Red", 2.2);
        Shape rectangle = new Rectangle("Yellow", 2, 4);

        System.out.println(circle.toString());
        System.out.println(rectangle.toString());
    }
}
