package com.vladproduction._12_concurrency.condition_objects_bank_example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Expected Behavior:
 * When you run the TransferringSimulator, it will create 10 bank accounts and attempt to transfer different random amounts between them concurrently.
 * You will see output representing the initial and final state of the bank accounts, including any transfers conducted by various threads.
 * */
public class TransferringSimulator {

    private static final int NUMBERS_ACCOUNT = 10;
    private static final double INITIAL_BALANCE_ACCOUNT = 1000;
    private static final int DELAY_TRANSACTION_WORK = 500;

    public static void main(String[] args) {

        //create bank object:
        Bank bank = new Bank(NUMBERS_ACCOUNT, INITIAL_BALANCE_ACCOUNT);
        System.out.println("Initial bank state: ");
        System.out.println(bank);

        // Create executor with a thread pool matching account count
        ExecutorService service = Executors.newFixedThreadPool(NUMBERS_ACCOUNT);

        //create tasks
        for (int i = 0; i < NUMBERS_ACCOUNT; i++) {
            int fromAccount = i;
            service.submit(()->{
                int toAccount;
                // Ensure the destination account is different
                do{
                    toAccount = (int)(bank.size() * Math.random());
                }while (toAccount == fromAccount); // Ensure different accounts for transfer

                // Generate a random transfer amount
                double amount = INITIAL_BALANCE_ACCOUNT * Math.random();

                // Attempt to transfer funds
                try {
                    bank.transfer(fromAccount, toAccount, amount);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }

                // Optionally sleep to simulate work being done
                try {
                    Thread.sleep((int) (DELAY_TRANSACTION_WORK * Math.random()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            });

        }

        // Shutdown the executor and wait for tasks to complete
        service.shutdown();
        while (!service.isTerminated()){
            // Wait for all tasks to complete
        }

        // Print the final state of the bank after transactions
        System.out.println("Final bank state:");
        bank.accountsInfo();

    }
}
