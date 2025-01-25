package com.vladproduction._12_concurrency.synchronizing_approaches;

public class SynchronizedThisExample {
    private final int[] array;

    public SynchronizedThisExample(int size) {
        this.array = new int[size];
    }

    public void increment(int index) {
        synchronized (this) {
            for (int i = 0; i < 100_000; i++) {
                array[index]++;
            }
        }
    }

    public void printArray() {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SynchronizedThisExample example = new SynchronizedThisExample(1);
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
