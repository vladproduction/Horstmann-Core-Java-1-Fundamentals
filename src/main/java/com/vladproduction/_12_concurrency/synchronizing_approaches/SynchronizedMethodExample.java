package com.vladproduction._12_concurrency.synchronizing_approaches;

public class SynchronizedMethodExample {
    private final int[] array;

    public SynchronizedMethodExample(int size) {
        array = new int[size];
    }

    // Method to increment a specific index
    public synchronized void increment(int index) {
        for (int i = 0; i < 100_000; i++) {
            array[index]++; // Increment the value at the specified index
        }
    }

    // Print the values in the array
    public void printArray() {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SynchronizedMethodExample example = new SynchronizedMethodExample(1); // Array of size 1

        // Create two threads that increment the same index
        Runnable task = () -> {
            example.increment(0);  // Increment the first index
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);

        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for both threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print final value in the array
        System.out.println("Final value in the array: ");
        example.printArray(); // Output the final value
    }
}
