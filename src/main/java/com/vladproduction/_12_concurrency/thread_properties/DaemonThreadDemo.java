package com.vladproduction._12_concurrency.thread_properties;

public class DaemonThreadDemo {

    public static void main(String[] args) {
        // Creating a user thread
        Thread userThread = new Thread(new UserTask(), "User-Thread");
        userThread.start();

        // Creating a daemon thread
        Thread daemonThread = new Thread(new DaemonTask(), "Daemon-Thread");
        daemonThread.setDaemon(true); // Setting the thread as a daemon
        daemonThread.start();

        try {
            // Waiting for the user thread to finish
            userThread.join();
            System.out.println("User thread has finished execution. Exiting main thread.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class UserTask implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running.");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " - Working: " + (i + 1));
                Thread.sleep(500);  // Simulating work
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        }
        System.out.println(Thread.currentThread().getName() + " has finished execution.");
    }
}

class DaemonTask implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " is performing background work.");
            try {
                Thread.sleep(300); // Simulating background work
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted.");
                break; // Exit if interrupted
            }
        }
    }
}
