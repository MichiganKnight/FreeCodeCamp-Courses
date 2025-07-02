package main.java.basicMultithreading;

public class ThreadPriorityExample {
    public static void main(String[] args) {
        //System.out.println(Thread.currentThread().getName());
        //System.out.println(Thread.currentThread().getPriority());

        //Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        //System.out.println(Thread.currentThread().getPriority());

        System.out.println(Thread.currentThread().getName() + " Says Hello");

        Thread threadOne = new Thread(() -> {
            System.out.println("Tread Two Says Hello Too");
        });

        threadOne.setPriority(Thread.MIN_PRIORITY);
        threadOne.start();
    }
}
