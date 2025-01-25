package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExecutorExampleGetFuture {
    private Lock lock = new ReentrantLock();
    private static int counter = 0;

    public ExecutorExampleGetFuture() {
    }

    private void increment(){
        lock.lock();
        try{
            counter++;
        }finally {
            lock.unlock();
        }
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {

        ExecutorExampleGetFuture example = new ExecutorExampleGetFuture();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Create a list of futures to track the submitted tasks
        Future<?> future1 = executor.submit(example::increment);
        Future<?> future2 = executor.submit(example::increment);

        // Wait for both tasks to complete
        try {
            future1.get(); // Wait for the first task to complete
            future2.get(); // Wait for the second task to complete
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Now it's safe to retrieve and print the final result
        System.out.println("Final result: " + example.getCounter());
        executor.shutdown(); // Shutdown the executor service
    }
}
