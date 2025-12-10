package com.revature.weekEight;

public class WorkerThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("State of " + ThreadStateDemo.thread1.getName() + " While it Called join() Method on Thread2: " + ThreadStateDemo.thread1.getState());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class ThreadStateDemo implements Runnable {
        public static Thread thread1;
        public static ThreadStateDemo obj;

        public static void main(String[] args) {
            System.out.println("=== Thread State Demo ===");

            obj = new ThreadStateDemo();
            thread1 = new Thread(obj);

            System.out.println("State of " + thread1.getName() + " After Creation: " + thread1.getState());
            thread1.start();

            System.out.println("State of " + thread1.getName() + " After Start: " + thread1.getState());
        }

        public void run() {
            WorkerThread workerThread = new WorkerThread();
            Thread thread2 = new Thread(workerThread);

            System.out.println("State of " + thread2.getName() + " After Creation: " + thread2.getState());
            thread2.start();

            System.out.println("State of " + thread2.getName() + " After Start: " + thread2.getState());

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("State of " + thread2.getName() + " After Sleep: " + thread2.getState());

            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("State of " + thread2.getName() + " After Execution Completed: " + thread2.getState());
        }
    }
}
