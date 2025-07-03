package main.java.threadSynchronization;

public class SynchronizationDemo {
    private static int counter = 0;

    public static void main(String[] args) {
        Thread threadOne = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        });

        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
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

        System.out.println("Counter: " + counter);
    }

    /**
     * Increments the "counter" variable in a thread-safe manner.
     * This method is synchronized to ensure that only one thread can access
     * the "counter" increment operation at a time, preventing race conditions
     * when multiple threads are incrementing the counter simultaneously.
     */
    private synchronized static void increment() {
        counter++;
    }
}

/*
    Step 1. Load
    Step 2. Increment
    Step 3. Set Back Value

    counter = 0; incrementValue = 1; < Thread 1
    counter = 0; incrementValue = 1 (2); < Thread 2
 */