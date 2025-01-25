package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock:
 * -Application has more read operations than write operations
 * -It allows multiple threads to read concurrently while ensuring exclusive access for writes.
 *
 * This example demonstrates the use of a ReadWriteLock to allow concurrent read access while ensuring exclusive write access;
 * Example: Inventory Management System
 * Imagine you have an inventory management system where multiple threads can read the current stock levels while some threads
 * may need to update those stock levels. The ReadWriteLock will help manage these concurrent operations effectively.
 * */
public class ReadWriteLockExample {
    public static void main(String[] args) throws InterruptedException {

        Inventory inventory = new Inventory();

        //create thread to write updates for stock:
        Thread writerThread = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                inventory.updateStock(i * 10);
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }

            }
        });

        //create multiple threads to read updates from stock:
        Thread readerTask = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                inventory.getStock();
                try{
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }

            }
        });

        Thread readerThread1 = new Thread(readerTask);
        Thread readerThread2 = new Thread(readerTask);
        Thread readerThread3 = new Thread(readerTask);

        // Start threads
        writerThread.start();
        readerThread1.start();
        readerThread2.start();
        readerThread3.start();

        // Wait for all threads to finish
        writerThread.join();
        readerThread1.join();
        readerThread2.join();
        readerThread3.join();
    }
}

class Inventory {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private int stockLevel;

    // Method to write/update the stock level
    public void updateStock(int newStockLevel) {
        rwLock.writeLock().lock();
        try {
            stockLevel = newStockLevel; // Only one thread can write
            System.out.println("Stock updated to: " + stockLevel);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    // Method to read the stock level
    public int getStock() {
        rwLock.readLock().lock();
        try {
            System.out.println("Reading stock level: " + stockLevel);
            return stockLevel; // Multiple threads can read concurrently
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
