package com.vladproduction._12_concurrency.what_are_threads;

import java.util.Arrays;

public class ThreadTestBank {

    public static final int DELAY = 10;
    public static final int STEPS = 100;
    public static final double MAX_AMOUNT = 1000;

    public static void main(String[] args) {

        var bank = new Bank(4, 100_000);

        //two tasks through Runnable creating:

        Runnable task1 = ()->{
            try{
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
                    Thread.sleep((int)(DELAY * Math.random()));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };

        Runnable task2 = ()->{
            try{
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(2, 3, amount);
                    Thread.sleep((int)(DELAY * Math.random()));
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };

        new Thread(task1).start();
        new Thread(task2).start();

    }

}

class Bank {

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
