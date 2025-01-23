package com.vladproduction._12_concurrency.what_are_threads;

public class ThreadTest {

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
