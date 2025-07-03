package main.java.threadSynchronization;

public class LockWithCustomObjects {
    private static int counterOne = 0;
    private static int counterTwo = 0;

    private static final Object lockOne = new Object();
    private static final Object lockTwo = new Object();

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

    private static void incrementOne() {
        synchronized (lockOne) {
            counterOne++;
        }
    }

    private static void incrementTwo() {
        synchronized (lockTwo) {
            counterTwo++;
        }
    }
}
