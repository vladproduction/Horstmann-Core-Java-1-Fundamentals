package com.vladproduction._12_concurrency.thread_states;

import java.util.concurrent.TimeUnit;

/**
 * In Java, a thread can exist in several states during its lifecycle. The key states defined by the Java Virtual Machine (JVM) are:
 *
 * New: A thread that has been created but not yet started.
 * Runnable: A thread that is ready to run and waiting for CPU time.
 * Blocked: A thread that is blocked waiting for a monitor lock (e.g., waiting to enter a synchronized block).
 * Waiting: A thread that is waiting indefinitely for another thread to perform a particular action (e.g., using Object.wait() or Thread.join()).
 * Timed Waiting: A thread that is waiting for another thread for a specified period (e.g., using Thread.sleep() or Thread.join(long millis)).
 * Terminated: A thread that has completed its execution.
 * */
public class ThreadStateDemo {

    public static void main(String[] args) throws InterruptedException {
        // Create a new thread (New State)
        Thread thread1 = new Thread(new RunnableTask(), "Thread-1");

        // Start the thread (Runnable State)
        System.out.println(thread1.getName() + " state: " + thread1.getState());
        thread1.start();
        System.out.println(thread1.getName() + " state after starting: " + thread1.getState());

        // Wait for the thread to enter the Timed Waiting state
        Thread.sleep(100); // Ensure thread1 is running

        // Check if thread1 is in Runnable State or Blocked State
        System.out.println(thread1.getName() + " state during execution: " + thread1.getState());

        // Sleep the main thread to allow the runnable thread to finish its task
        TimeUnit.SECONDS.sleep(2);
        System.out.println(thread1.getName() + " state after completion: " + thread1.getState());
    }

    static class RunnableTask implements Runnable {
        @Override
        public void run() {
            try {
                // Entering Timed Waiting state
                System.out.println(Thread.currentThread().getName() + " sleeping for 1 second.");
                Thread.sleep(1000); // Timed Waiting state
                System.out.println(Thread.currentThread().getName() + " woke up from sleep.");

                synchronized (this) {
                    // Synchronization block to demonstrate Blocked state
                    System.out.println(Thread.currentThread().getName() + " acquired the lock, entering synchronized block.");
                    Thread.sleep(1000); // Simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
            System.out.println(Thread.currentThread().getName() + " has finished execution.");
        }
    }
}
