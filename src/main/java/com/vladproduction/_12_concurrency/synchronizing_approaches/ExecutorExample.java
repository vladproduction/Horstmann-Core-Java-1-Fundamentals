package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * concurrent incrementing of the counter using a loop and multiple worker threads
 * */
public class ExecutorExample {
    private Lock lock = new ReentrantLock();
    private static int counter = 0;
    private static final int NUM_INCREMENT_TASKS = 100; // Number of increments per worker
    private static final int NUM_WORKERS = 10; // Number of worker threads

    public ExecutorExample() {
    }

    private void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        ExecutorExample example = new ExecutorExample();

        ExecutorService executor = Executors.newFixedThreadPool(NUM_WORKERS);
        
        // Create and submit increment tasks
        for (int i = 0; i < NUM_WORKERS; i++) {
            executor.submit(() -> {
                for (int j = 0; j < NUM_INCREMENT_TASKS; j++) {
                    example.increment(); // Increment counter in each task
                }
            });
        }

        // Shutdown the executor service and wait for tasks to complete
        executor.shutdown();

        // Block until all tasks are complete
        // The main thread waits for the executor to terminate, which means all tasks have completed before proceeding
        // to retrieve and print the final counter value.
        while (!executor.isTerminated()) {
            // Just wait
        }

        // Now it's safe to retrieve and print the final result
        System.out.println("Final result: " + example.getCounter()); // Should be NUM_INCREMENT_TASKS * NUM_WORKERS
    }
}
