package com.revature.weekSix.unitTesting.bankTest;

public class Account {
    public double balance;

    public Account() {
        balance = 0.0;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public double withdraw(double amount) {
        if (amount > balance) {
            balance = balance - amount;

            return balance;
        }

        return 0.0;
    }

    public double getBalance() {
        return balance;
    }
}
