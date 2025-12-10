package com.revature.weekEight;

public class MultiThreadingWithThreadDemo extends Thread {
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getId() + " is Running");
        } catch (Exception e) {
            System.out.println("Exception Caught");
        }
    }

    private static class MultiThread {
        public static void main(String[] args) {
            System.out.println("=== Multi-Threading with Thread Example ===");

            int n = 8;
            for (int i = 0; i < n; i++) {
                MultiThreadingWithThreadDemo object = new MultiThreadingWithThreadDemo();
                object.start();
            }
        }
    }

    private static class MultiThreadingWithRunnableDemo {
        public static void main(String[] args) {
            System.out.println("=== Multi-Threading with Runnable Example ===");

            int n = 8;
            for (int i = 0; i < n; i++) {
                Thread t = new Thread(new MultiThreadingWithThreadDemo());
                t.start();
            }
        }
    }
}