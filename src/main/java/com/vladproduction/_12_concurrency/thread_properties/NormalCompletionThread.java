package com.vladproduction._12_concurrency.thread_properties;

public class NormalCompletionThread extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(getName() + " is running: " + i);
                Thread.sleep(500); // Simulating work
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted during sleep.");
        } finally {
            System.out.println(getName() + " has finished execution normally.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NormalCompletionThread thread = new NormalCompletionThread();
        thread.start();
        
        // Wait for a moment before interrupting
        Thread.sleep(1000); // Allow some progress
        thread.interrupt(); // Attempt to interrupt the thread
        thread.join(); // Wait for the thread to finish
    }
}
