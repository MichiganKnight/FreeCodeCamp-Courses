package com.revature.weekThree;

public class OCP {
    public static void main(String[] args) {
        System.out.print("=== Open-Closed Principle (OCP) ===");
    }

    interface Vehicle {
        public double accelerate();
    }

    static class Car implements Vehicle {
        int speed;

        @Override
        public double accelerate() {
            this.speed += 10;

            return this.speed;
        }
    }

    static class Truck implements Vehicle {
        int speed;

        @Override
        public double accelerate() {
            this.speed += 5;

            return this.speed;
        }
    }
}
