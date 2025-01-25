package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    private final Lock lock = new ReentrantLock();
    private final int[] array;

    public ReentrantLockExample(int size) {
        this.array = new int[size];
    }

    public void increment(int index) {
        try {
            lock.lock();
            for (int i = 0; i < 100_000; i++) {
                array[index]++;
            }
        } finally {
            lock.unlock();
        }
    }

    public void printArray() {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ReentrantLockExample example = new ReentrantLockExample(1);
        Runnable task = () -> {
            example.increment(0);
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final result: ");
        example.printArray();
    }

}
