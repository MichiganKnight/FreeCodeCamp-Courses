package com.revature.weekEight;

public class SynchronizationExample {
    int value = 0;

    public static void main(String[] args) {
        System.out.println("=== Synchronization Example ===");

        SynchronizationExample object = new SynchronizationExample();

        Thread thread1 = new Thread(() -> {
            try {
                object.updateValueFiveTimesByAddingOne();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                object.updateValueFiveTimesByAddingTwo();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
    }

    public void updateValueFiveTimesByAddingOne() throws InterruptedException {
        System.out.println("Print Statement Before addingOne Loop");

        synchronized (this) {
            for (int i = 1; i <= 5; i++) {
                System.out.println(++value);
                Thread.sleep(500);
            }
        }

        System.out.println("Print Statement After addingOne Loop");
    }

    public void updateValueFiveTimesByAddingTwo() throws InterruptedException {
        System.out.println("Print Statement Before addingTwo Loop");

        synchronized (this) {
            for (int i = 1; i <= 5; i++) {
                System.out.println(value += 2);
                Thread.sleep(500);
            }
        }

        System.out.println("Print Statement After addingTwo Loop");
    }
}
