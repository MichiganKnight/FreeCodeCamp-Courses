package main.java.threadSynchronization;

public class WaitAndNotifyDemo {
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        Thread threadOne = new Thread(() -> {
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread threadTwo = new Thread(() -> {
            try {
                two();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        threadOne.start();
        threadTwo.start();
    }

    private static void one() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello From Method 1...");

            LOCK.wait();

            System.out.println("Back Again In Method 2...");
        }
    }

    private static void two() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello From Method 2...");

            LOCK.notify();

            System.out.println("Back Again In Method 2... Post Notify");
        }
    }
}