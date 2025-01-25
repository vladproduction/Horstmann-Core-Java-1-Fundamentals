package com.vladproduction._12_concurrency.what_are_threads;

import com.vladproduction._12_concurrency.what_are_threads.BankLogged;

import java.util.Arrays;

public class ThreadTestBankLogged {

    public static final int DELAY = 10;
    public static final int STEPS = 100;
    public static final double MAX_AMOUNT = 1000;

    public static void main(String[] args) {
        var bank = new BankLogged(4, 100_000);

        // Two tasks through Runnable creating:
        Runnable task1 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable task2 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(2, 3, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        try {
            t1.join(); // Wait for task1 to finish
            t2.join(); // Wait for task2 to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print all logs after all threads are done
        System.out.println(bank.getLog());
    }
}

class BankLogged {

    private final double[] accounts;
    private StringBuilder log = new StringBuilder(); // StringBuilder to hold log messages

    public BankLogged(int n, double initialBalance){
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    // Transfer method
    public void transfer(int from, int to, double amount){
        if (accounts[from] < amount) return;
        log.append(Thread.currentThread());
        accounts[from] -= amount;
        log.append(String.format(" %10.2f from %d to %d", amount, from, to));
        accounts[to] += amount;
        log.append(String.format(" Total Balance: %10.2f%n", getTotalBalance()));
    }

    // Method to get the sum of all account balances
    public double getTotalBalance(){
        double sum = 0;
        for (double account : accounts) {
            sum += account;
        }
        return sum;
    }

    public int size(){
        return accounts.length;
    }

    public String getLog() { // Method to get log as a string
        return log.toString();
    }

}

