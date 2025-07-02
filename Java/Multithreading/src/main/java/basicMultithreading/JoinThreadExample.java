package main.java.basicMultithreading;

public class JoinThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
           for (int i = 0; i < 5; i++) {
               System.out.println("Thread One: " + i);
           }
        });

        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                System.out.println("Thread Two: " + i);
            }
        });

        System.out.println("Starting Threads");

        threadOne.start();
        threadTwo.start();

        //threadOne.join();
        //threadTwo.join();

        waitForCompletion(threadOne);
        waitForCompletion(threadTwo);

        System.out.println("Done Executing Threads");
    }

    private static void waitForCompletion(Thread thread) throws InterruptedException {
        thread.join();
    }
}
