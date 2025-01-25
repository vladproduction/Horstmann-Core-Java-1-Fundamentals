package com.vladproduction._12_concurrency.what_are_threads;

import java.util.Arrays;

public class SimpleBank {
    private final double[] accounts;

    public SimpleBank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    // Transfer method without synchronization
    public void transfer(int from, int to, double amount) {
        if (accounts[from] < amount) return; // Check if sufficient balance
        accounts[from] -= amount; // Deduct amount
        accounts[to] += amount;   // Add amount to recipient
        System.out.printf("Transferred %.2f from account %d to account %d%n", amount, from, to);
    }

    // Display total balance
    public double getTotalBalance() {
        double sum = 0;
        for (double account : accounts) {
            sum += account;
        }
        return sum;
    }

    public static void main(String[] args) {
        SimpleBank bank = new SimpleBank(4, 1000);

        // Task to transfer money with predictable amounts
        Runnable task = () -> {
            // Fixed amounts for transfers
            double[] amounts = {100, 200, 150, 300, 400, 50, 250, 75, 125, 175};

            for (int i = 0; i < amounts.length; i++) {
                bank.transfer(0, 1, amounts[i]); // Transfer from account 0 to account 1
                // Add a small delay to increase likelihood of race conditions
                try {
                    Thread.sleep(500); // Fixed delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start threads
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print final total balance
        System.out.printf("Total Balance: %.2f%n", bank.getTotalBalance());
    }
}
