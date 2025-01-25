package com.vladproduction._12_concurrency.condition_objects_simple;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(100.0); // Initial balance of 100.0

        ExecutorService executor = Executors.newFixedThreadPool(3); // Create a thread pool

        // Simulate deposits
        executor.execute(() -> {
            account.deposit(50.0); // Deposit 50.0
        });

        // Simulate withdrawals
        executor.execute(() -> {
            try {
                account.withdraw(120.0); // Attempt to withdraw 120.0
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.execute(() -> {
            try {
                account.withdraw(30.0); // Attempt to withdraw 30.0
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.shutdown(); // Shutdown the executor service
    }
}
