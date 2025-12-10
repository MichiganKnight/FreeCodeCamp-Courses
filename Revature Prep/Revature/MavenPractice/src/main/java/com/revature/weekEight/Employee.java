package com.revature.weekEight;

public class Employee extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " is Working...");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();

                break;
            }
        }
    }

    static class ThreadDriver {
        public static void main(String[] args) {
            System.out.println("=== Threading Example ===");

            Employee emp1 = new Employee();
            emp1.setPriority(1);
            emp1.start();

            Employee emp2 = new Employee();
            emp2.setPriority(2);
            emp2.start();

            try {
                emp1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(emp1.getPriority());
            System.out.println(emp2.getPriority());

            System.out.println(emp1.isAlive());
            System.out.println(emp2.isAlive());
        }
    }
}
