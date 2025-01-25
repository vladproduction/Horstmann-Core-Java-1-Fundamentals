package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIncrementExample {
    public static final int FINAL_INCREMENTING_VALUE = 100_000;
    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        for (int i = 0; i < FINAL_INCREMENTING_VALUE; i++) {
            counter.incrementAndGet(); // Atomically increments the current value by 1
        }
    }

    public int getCounter() {
        return counter.get(); // Get the current value
    }

    public static void main(String[] args) {
        //instance to work with
        AtomicIncrementExample example = new AtomicIncrementExample();

        //create tasks objects:
        //lambda example structure:
        Runnable taskLambda = ()->{
            example.increment();
        };

        //method reference example:
        Runnable taskMethodReference = example::increment;

        //create threads and submitting our tasks
        Thread threadLambda1 = new Thread(taskLambda);
        Thread threadLambda2 = new Thread(taskLambda);

        Thread threadTaskMethodReference1 = new Thread(taskMethodReference);
        Thread threadTaskMethodReference2 = new Thread(taskMethodReference);
        Thread threadTaskMethodReference3 = new Thread(taskMethodReference);

        Thread[] threads = {
                threadLambda1,
                threadLambda2,
                threadTaskMethodReference1,
                threadTaskMethodReference2,
                threadTaskMethodReference3
        };

        startThreads(threads);

        joiningThreads(threads);

        int exampleCounterResult = example.getCounter();
        System.out.println("Tasks taken and incrementing the shared resource: " + threads.length);
        System.out.println("Final result: " + exampleCounterResult);


    }

    private static void joiningThreads(Thread[] threads) {
        try{
            for (Thread thread : threads) {
                thread.join();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private static void startThreads(Thread... threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
