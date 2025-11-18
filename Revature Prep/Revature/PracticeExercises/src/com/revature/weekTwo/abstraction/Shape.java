package com.revature.weekTwo.abstraction;

public abstract class Shape {
    String color;

    abstract double area();
    public abstract String toString();

    public Shape(String color) {
        System.out.println("Shape Constructor Called");

        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
