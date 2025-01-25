package com.vladproduction._12_concurrency.synchronized_keyword_concept;

import java.util.Arrays;

/**
 * A bank with a number of bank accounts that uses synchronization primitives.
 * */
public class BankSyncs {
    private final double[] accounts;

    // Constructs the bank
    public BankSyncs(int n, double initAmount){
        accounts = new double[n];
        Arrays.fill(accounts, initAmount);
    }

    //method to transfer money from one account to another
    public synchronized void transfer(int from, int to, double amount) throws InterruptedException {

        while (accounts[from] < amount){
            wait();                         // wait on intrinsic object lock's single condition
        }

        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());

        notifyAll();                        // notify all threads waiting on the condition
    }

    // method gets the sum of all account balances
    public synchronized double getTotalBalance() {
        double sum = 0;
        for (double account : accounts) {
            sum += account;
        }
        return sum;
    }

    //method return the number of accounts
    public int size(){
        return accounts.length;
    }

    //method to iterate through accounts:
    public void accountsInfo(){
        for (double account : accounts) {
            System.out.printf("%10.2f", account);
        }
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return "Bank " +
                "accounts " + Arrays.toString(accounts);
    }
}


















