package com.vladproduction._12_concurrency.what_are_threads.alternatelogging;

import java.util.Arrays;

public class Bank {

    private final double[] accounts;
    private StringBuilder log = new StringBuilder(); // StringBuilder to hold log messages

    public Bank(int n, double initialBalance){
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

