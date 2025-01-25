package com.vladproduction._12_concurrency.synchronized_keyword_concept;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankSyncsApp {
    private static final int NUM_ACCOUNTS = 10;                // Number of bank accounts
    private static final double INITIAL_BALANCE = 100_000;     // Initial balance for each account
    private static final int DELAY_TRANSACTION_WORK = 500;      // Delay to simulate transaction work

    public static void main(String[] args) {
        // Create the bank with a specified number of accounts
        BankSyncs bank = new BankSyncs(NUM_ACCOUNTS, INITIAL_BALANCE);
        System.out.println("Initial bank state:");
//        System.out.println(bank); // Display initial state of the bank
        bank.accountsInfo();

        // Create an executor with a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(NUM_ACCOUNTS);

        // Create and submit tasks for transferring funds
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            int fromAccount = i;
            executor.submit(() -> {
                int toAccount;
                // Ensure the destination account is different from the source
                do {
                    toAccount = (int) (Math.random() * NUM_ACCOUNTS);
                } while (toAccount == fromAccount);

                // Generate a random transfer amount. Make sure it's less than or equal to the INITIAL_BALANCE
                double amount = Math.random() * (INITIAL_BALANCE / 2);

                // Attempt to transfer funds
                try {
                    bank.transfer(fromAccount, toAccount, amount);
                } catch (InterruptedException e) {
                    // Restore the interrupted status
                    Thread.currentThread().interrupt();
                    System.out.println("Thread interrupted: " + Thread.currentThread());
                }

                // Simulate work done after transfer
                try {
                    Thread.sleep((int) (DELAY_TRANSACTION_WORK * Math.random()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            });
        }

        // Shutdown the executor and wait for tasks to complete
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all tasks to complete
        }

        // Print the final state of the bank after transactions
        System.out.println("Final bank state:");
        bank.accountsInfo();
    }
}