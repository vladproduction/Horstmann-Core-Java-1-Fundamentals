package com.vladproduction._12_concurrency.condition_objects_bank_example;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A bank with a number of bank accounts that uses locks for serializing access.
 * */
public class Bank {
    private final double[] accounts;
    private final Lock bankLock = new ReentrantLock();
    private final Condition sufficientFunds = bankLock.newCondition();

    //constructor:
    public Bank(int n, double initialBalance){
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    //method for transfers money from one account to another
    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try{
            while (accounts[from] < amount){
                System.out.printf("Thread %s: Waiting for sufficient funds to transfer %.2f from account %d to account %d%n",
                        Thread.currentThread(), amount, from, to);
                sufficientFunds.await();
            }
            System.out.println(Thread.currentThread());
            accounts[from] -= amount; // Deduct amount from 'from' account
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount; // Add amount to 'to' account
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll(); // Notify other threads
        }finally {
            bankLock.unlock();
        }

    }

    //method gets the sum of all account balances
    public double getTotalBalance() {
        bankLock.lock();
        try {
            double sum = 0.0;
            for (double account : accounts) {
                sum += account;
            }
            return sum;
        }finally {
            bankLock.unlock();
        }
    }

    //method gets the number of accounts in the bank
    public int size(){
        return accounts.length;
    }

    //method to iterate through accounts:
    public void accountsInfo(){
        for (double account : accounts) {
            System.out.printf("%10.2f", account);
        }
    }

    @Override
    public String toString() {
        return "Bank " +
                "accounts " + Arrays.toString(accounts);
    }
}
