package com.revature.weekThree;

public class SolidPrinciple {
    public static void main(String[] args) {
        System.out.println("=== SOLID Principle ===");

        SRP.UserDAO userDAO = new SRP.UserDAO();
        userDAO.createUser("admin", "admin");

        SRP.UserAuthenticator authenticator = new SRP.UserAuthenticator();
        authenticator.authenticateUser("admin", "admin");

        OCP.Car car = new OCP.Car();
        car.accelerate();

        OCP.Truck truck = new OCP.Truck();
        truck.accelerate();

        LSP.Penguin penguin = new LSP.Penguin();
        penguin.fly();

        ISP.Bike bike = new ISP.Bike();
        bike.accelerate();
        bike.breakVehicle();

        ISP.Truck ispTruck = new ISP.Truck();
        ispTruck.accelerate();
        ispTruck.breakVehicle();
        ispTruck.openDoors();
    }
}
