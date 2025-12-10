package com.revature.weekEight;

import java.io.FileNotFoundException;

public class RunnableExample {
    public static void main(String[] args) {
        System.out.println("=== Runnable Example ===");
        System.out.println("Main Thread: " + Thread.currentThread().getName());

        Thread t1 = new Thread(new RunnableImpl());
        t1.start();
    }

    private static class RunnableImpl implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is Running... | Executing run() Method");

            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found Exception Caught!");

                e.printStackTrace();
            }
        }
    }
}
