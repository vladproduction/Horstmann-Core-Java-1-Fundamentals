package com.vladproduction._12_concurrency.lock_reentrantlock;

public class LockBankTest {

    public static final int N_ACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;
    public static final int DELAY = 10;

    public static void main(String[] args) {

        var bank = new BankAccountWithLock(N_ACCOUNTS, INITIAL_BALANCE);

        for (int i = 0; i < N_ACCOUNTS; i++) {
            int fromAccount = i;
            Runnable r = ()->{
                try{
                    while (true){
                        int toAccount = (int)(bank.size() * Math.random());
                        double amount = MAX_AMOUNT * Math.random();
                        bank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int) (DELAY* Math.random()));
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            };
            Thread t = new Thread(r);
            t.start();
        }

    }
}
