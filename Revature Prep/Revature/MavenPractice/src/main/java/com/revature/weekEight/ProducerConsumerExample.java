package com.revature.weekEight;

import java.util.ArrayDeque;

public class ProducerConsumerExample {
    public static ArrayDeque<Cookie> cookies = new ArrayDeque<>();
    public static final int MAX_COOKIES = 5;

    public static void main(String[] args) {
        System.out.println("=== Producer-Consumer Example ===");

        Baker baker = new Baker();
        Cashier cashier = new Cashier();

        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    baker.produce(new Cookie());
                }
            } catch (InterruptedException e) {
                System.out.println("Producer Interrupted");
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    cashier.consume();
                }
            } catch (InterruptedException e) {
                System.out.println("Consumer Interrupted");
            }
        });

        thread1.start();
        thread2.start();
    }

    private static class Cookie {
    }

    private static class Baker {
        ArrayDeque<Cookie> bakerCookies = ProducerConsumerExample.cookies;

        public void produce(Cookie cookie) throws InterruptedException {
            synchronized (bakerCookies) {
                while (bakerCookies.size() == ProducerConsumerExample.MAX_COOKIES) {
                    System.out.println("Baker Waits!");
                    bakerCookies.wait();
                }

                bakerCookies.add(cookie);
                System.out.println("Baked Baked a Cookie!\n" + "Cookies Currently For Sale: " + bakerCookies.size());
                bakerCookies.notify();
            }
        }
    }

    private static class Cashier {
        ArrayDeque<Cookie> cashierCookies = ProducerConsumerExample.cookies;

        public void consume() throws InterruptedException {
            synchronized (cashierCookies) {
                while (cashierCookies.isEmpty()) {
                    System.out.println("Cashier Waits!");
                    cashierCookies.wait();
                }

                cashierCookies.remove();
                System.out.println("Cashier Sold a Cookie!\n" + "Cookies Currently For Sale: " + cashierCookies.size());
                cashierCookies.notify();
            }
        }
    }
}

