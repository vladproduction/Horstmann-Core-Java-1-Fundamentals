package com.vladproduction._12_concurrency.condition_objects_simple;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private double balance; // Account balance
    private final Lock bankLock = new ReentrantLock(); // Lock for managing access
    private final Condition sufficientFunds = bankLock.newCondition(); // Condition for sufficient funds

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        bankLock.lock(); // Acquire the lock
        try {
            balance += amount; // Deposit money
            System.out.printf("Deposited: %.2f | New Balance: %.2f%n", amount, balance);
            sufficientFunds.signalAll(); // Notify waiting threads that funds are available
        } finally {
            bankLock.unlock(); // Always unlock in a finally block
        }
    }

    public void withdraw(double amount) throws InterruptedException {
        bankLock.lock(); // Acquire the lock
        try {
            while (balance < amount) { // Check for sufficient funds
                System.out.printf("Insufficient funds to withdraw %.2f | Current Balance: %.2f. Waiting...%n", amount, balance);
                sufficientFunds.await(); // Wait until notified that funds are available
            }

            balance -= amount; // Withdraw money
            System.out.printf("Withdrew: %.2f | New Balance: %.2f%n", amount, balance);
        } finally {
            bankLock.unlock(); // Always unlock in a finally block
        }
    }

    public double getBalance() {
        return balance; // Return current balance
    }
}
