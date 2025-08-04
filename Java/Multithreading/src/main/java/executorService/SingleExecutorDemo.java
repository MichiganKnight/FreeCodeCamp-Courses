package main.java.executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleExecutorDemo {
    public static void main(String[] args) {
        System.out.println("=== Single Thread Executor Demo ===");

        try (ExecutorService service = Executors.newSingleThreadExecutor()) {
            for (int i = 0; i < 5; i++) {
                service.execute(new Task(i));
            }
        }
    }
}

class Task implements Runnable {
    private final int TASK_ID;

    public Task(int taskId) {
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

        //System.out.println("Task: " + TASK_ID + " is Done Executing by Thread: " + Thread.currentThread().getName());
    }
}
