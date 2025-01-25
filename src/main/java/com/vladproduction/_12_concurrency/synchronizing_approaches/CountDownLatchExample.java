package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) {

        // number of worker threads to wait for:
        int numberOfWorkers = 5;

        // Create a CountDownLatch initialized to the number of workers
        CountDownLatch latch = new CountDownLatch(numberOfWorkers);

        // create and start worker threads:
        for (int i = 0; i < numberOfWorkers; i++) {
            final int workerId = i;
            Thread thread = new Thread(()->{
                try{
                    System.out.println("Worker " + workerId + " is doing job...");
                    Thread.sleep((long) (Math.random() * 1000));
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }finally {
                    System.out.println("Worker " + workerId + " is finish job.");
                    latch.countDown();
                }
            });
            thread.start();
        }

        try {
            // Main thread waits until the count of the latch reaches zero
            latch.await();
            System.out.println("All workers have finished. Main thread proceeding...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
