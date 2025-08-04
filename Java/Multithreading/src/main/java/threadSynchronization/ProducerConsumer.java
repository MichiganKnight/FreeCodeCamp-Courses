package main.java.threadSynchronization;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        Worker worker = new Worker(5, 0);

        Thread producer = new Thread(() -> {
            try {
                worker.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                worker.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();
    }
}

class Worker {
    private int sequence = 0;

    private final Integer TOP;
    private final Integer BOTTOM;
    private final List<Integer> CONTAINER;
    private final Object LOCK = new Object();

    public Worker(Integer top, Integer bottom) {
        this.TOP = top;
        this.BOTTOM = bottom;
        this.CONTAINER = new ArrayList<>();
    }

    public void produce() throws InterruptedException {
        synchronized (LOCK) {
            while (true) {
                if (CONTAINER.size() == TOP) {
                    System.out.println("Container Full - Waiting for Items to be Removed...");

                    LOCK.wait();
                } else {
                    System.out.println(sequence + " Added to the Container");

                    CONTAINER.add(sequence++);

                    LOCK.notify();
                }

                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (LOCK) {
            while (true) {
                if (CONTAINER.size() == BOTTOM) {
                    System.out.println("Container Empty - Waiting for Items to be Added...");

                    LOCK.wait();
                }
                else {
                    System.out.println(CONTAINER.removeFirst() + " Removed from the Container");

                    LOCK.notify();
                }

                Thread.sleep(500);
            }
        }
    }
}
