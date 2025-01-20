package com.vladproduction._12_concurrency.whatarethreads;

import java.util.Arrays;

public class Bank {

    private final double[] accounts;

    public Bank(int n, double initialBalance){
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    //transfer method
    public void transfer(int from, int to, double amount){
        if (accounts[from] < amount) return;
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
    }

    //method to gets the sum of all account balances
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

}
