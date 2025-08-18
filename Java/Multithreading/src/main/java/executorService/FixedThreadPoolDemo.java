package main.java.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolDemo {
    public static void main(String[] args) {
        System.out.println("\n=== Fixed Thread Pool Demo ===");

        try (ExecutorService service = Executors.newFixedThreadPool(2)) {
            for (int i = 0; i < 7; i++) {
                service.execute(new Work(i));
            }
        }
    }
}

class Work implements Runnable {
    private final int WORK_ID;

    public Work(int workId) {
        this.WORK_ID = workId;
    }

    @Override
    public void run() {
        System.out.println("Work ID: " + WORK_ID + " is Being Executed by Thread: " + Thread.currentThread().getName());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
