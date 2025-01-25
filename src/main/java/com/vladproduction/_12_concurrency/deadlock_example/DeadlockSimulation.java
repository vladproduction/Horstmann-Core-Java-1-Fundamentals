package com.vladproduction._12_concurrency.deadlock_example;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadlockSimulation {

    static class Bank {
        private final double[] accounts;
        private final Object[] locks;

        // Constructs the bank
        public Bank(int n, double initAmount) {
            accounts = new double[n];
            locks = new Object[n]; // Array to hold lock objects for each account

            Arrays.fill(accounts, initAmount);
            for (int i = 0; i < n; i++) {
                locks[i] = new Object(); // Initialize lock objects
            }
        }

        // Transfer money between accounts
        public void transfer(int from, int to, double amount) {
            Object lock1 = locks[from];
            Object lock2 = locks[to];

            // Lock in a fixed order to prevent deadlocks
            if (from < to) {
                synchronized (lock1) {
                    synchronized (lock2) {
                        executeTransfer(from, to, amount);
                    }
                }
            } else {
                synchronized (lock2) {
                    synchronized (lock1) {
                        executeTransfer(from, to, amount);
                    }
                }
            }
        }

        private void executeTransfer(int from, int to, double amount) {
            if (accounts[from] >= amount) {
                accounts[from] -= amount; // Deduct amount from 'from' account
                accounts[to] += amount; // Add amount to 'to' account
                System.out.printf("Transferred %.2f from account %d to account %d%n", amount, from, to);
                System.out.printf("Total Balance: %.2f%n", getTotalBalance());
            } else {
                System.out.printf("Insufficient funds to transfer %.2f from account %d to account %d.%n", amount, from, to);
            }
        }

        // Display the current state of the accounts
        public void printBalances() {
            System.out.println("Current account balances: " + Arrays.toString(accounts));
        }

        // Method to get the total balance of all accounts
        public double getTotalBalance() {
            double sum = 0.0;
            for (double account : accounts) {
                sum += account;
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank(2, 200.0); // Create a bank with two accounts

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Simulate thread for transferring money
        executor.submit(() -> {
            bank.transfer(0, 1, 300); // Thread 1 tries to transfer $300 from Account 0 to Account 1
        });

        executor.submit(() -> {
            bank.transfer(1, 0, 400); // Thread 2 tries to transfer $400 from Account 1 to Account 0
        });

        executor.shutdown(); // Shutdown the executor service
        while (!executor.isTerminated()) {
            // Wait for all tasks to complete
        }

        // Print the final state of the bank after all transactions
        bank.printBalances();
    }
}
