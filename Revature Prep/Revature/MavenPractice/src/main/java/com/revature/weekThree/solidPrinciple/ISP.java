package com.revature.weekThree.solidPrinciple;

public class ISP {
    public static void main(String[] args) {
        System.out.println("=== Interface Segregation Principle  (ISP) ===");
    }

    static class Vehicle {
        public void accelerate() {}
        public void breakVehicle() {}
    }

    interface Enterable {
        public void openDoors();
    }

    static class Bike extends Vehicle {
        public void accelerate() {

        }

        public void breakVehicle() {

        }
    }

    static class Truck extends Vehicle implements Enterable {
        public void accelerate() {

        }

        public void breakVehicle() {

        }

        public void openDoors() {

        }
    }
}
