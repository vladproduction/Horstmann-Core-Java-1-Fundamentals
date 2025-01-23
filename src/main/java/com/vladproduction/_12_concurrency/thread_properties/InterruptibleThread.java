package com.vladproduction._12_concurrency.thread_properties;

class InterruptibleThread extends Thread {

    public InterruptibleThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Check if the current thread has been interrupted
                if (Thread.interrupted()) {
                    System.out.println(getName() + " was interrupted (inside run method) and is exiting.");
                    break; // Exit the loop if interrupted
                }

                // Simulating some work
                System.out.println(getName() + " is working.");
                Thread.sleep(500); // Sleep method can throw InterruptedException
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " caught InterruptedException and is exiting.");
        } finally {
            System.out.println(getName() + " has finished execution.");
        }
    }

    public static void main(String[] args) {
        // Creating and starting threads
        InterruptibleThread thread1 = new InterruptibleThread("Thread-1");
        InterruptibleThread thread2 = new InterruptibleThread("Thread-2");

        thread1.start();
        thread2.start();

        // Allow threads to run for a moment
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupting the threads
        thread1.interrupt();
        thread2.interrupt();
    }
}
