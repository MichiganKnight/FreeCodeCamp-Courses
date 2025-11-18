package com.revature.weekTwo.exceptions;

public class Exceptions {
    public static void main(String[] args) {
        System.out.println("=== Exceptions ===");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("End of Finally Block");
        }

        System.out.println("Outside Try-Catch Block");

        Bicycle myBike = new Bicycle();
        System.out.println("Gear: " + myBike.gear + " | Speed: " + myBike.speed);

        myBike.speedUp(24);
        System.out.println("Gear: " + myBike.gear + " | Speed: " + myBike.speed);

        //myBike.slowDown(2);
        //System.out.println("Gear: " + myBike.gear + " | Speed: " + myBike.speed);

        try {
            myBike.slowDown(26);
        } catch (NegativeSpeedException e) {
            myBike.speed = 0;
            myBike.gear = 1;

            e.printStackTrace();
        }

        System.out.println("Gear: " + myBike.gear + " | Speed: " + myBike.speed);
    }
}
