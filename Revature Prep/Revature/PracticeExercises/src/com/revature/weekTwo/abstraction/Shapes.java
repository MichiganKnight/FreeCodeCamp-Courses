package com.revature.weekTwo.abstraction;

class Circle extends Shape {
    double radius;

    public Circle(String color, double radius) {
        super(color);
        System.out.println("Circle Constructor Called");

        this.radius = radius;
    }

    @Override double area() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override public String toString() {
        return "\nCircle Color: " + super.getColor() + " | Circle Area: " + area();
    }
}

class Rectangle extends Shape {
    double length, width;

    public Rectangle(String color, double length, double width) {
        super(color);
        System.out.println("Rectangle Constructor Called");

        this.length = length;
        this.width = width;
    }

    @Override double area() {
        return length * width;
    }

    @Override public String toString() {
        return "\nRectangle Color: " + super.getColor() + " | Rectangle Area: " + area();
    }
}
