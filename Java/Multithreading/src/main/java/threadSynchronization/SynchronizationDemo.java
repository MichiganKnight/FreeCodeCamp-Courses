package main.java.threadSynchronization;

public class SynchronizationDemo {
    private static int counterOne = 0;
    private static int counterTwo = 0;

    public static void main(String[] args) {
        Thread threadOne = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementOne();
            }
        });

        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementTwo();
            }
        });

        threadOne.start();
        threadTwo.start();

        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Counter: " + counterOne + " -- " + counterTwo);
    }

    private synchronized static void incrementOne() {
        counterOne++;
    }

    private synchronized static void incrementTwo() {
        counterTwo++;
    }
}

/*
    Step 1. Load
    Step 2. Increment
    Step 3. Set Back Value

    counter = 0; incrementValue = 1; < Thread 1
    counter = 0; incrementValue = 1 (2); < Thread 2
 */