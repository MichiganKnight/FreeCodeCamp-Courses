package main.java.basicMultithreading;

public class ExtendsThreadExample {
    public static void main(String[] args) {
        Thread threadOne = new Thread1();
        Thread threadTwo = new Thread2();

        threadOne.start();
        threadTwo.start();
    }
}

class Thread1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i ++) {
            System.out.println("Thread One: " + i);
        }
    }
}

class Thread2 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i ++) {
            System.out.println("Thread Two: " + i);
        }
    }
}