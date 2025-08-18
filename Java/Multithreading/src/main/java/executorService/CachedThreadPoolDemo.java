package main.java.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolDemo {
    public static void main(String[] args) {
        System.out.println("\n=== Cached Thread Pool Demo ===");

        try (ExecutorService service = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 1000; i++) {
                service.execute(new TaskOne(i));
            }
        }
    }
}

class TaskOne implements Runnable {
    private final int TASK_ID;

    public TaskOne(int taskId) {
        this.TASK_ID = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task: " + TASK_ID + " is Being Executed by Thread: " + Thread.currentThread().getName());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
