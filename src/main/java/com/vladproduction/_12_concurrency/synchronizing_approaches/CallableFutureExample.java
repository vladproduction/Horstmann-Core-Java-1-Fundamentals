package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableFutureExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5); // Create a thread pool

        // Array of numbers for which we want to calculate factorials
        int[] numbers = {5, 7, 10, 12, 15};

        // Create an array of Future objects to store the results
        Future<Long>[] futures = new Future[numbers.length];

        // Submit factorial tasks to the executor
        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            futures[i] = executor.submit(new Callable<Long>() {
                @Override
                public Long call() {
                    return factorial(number); // Calculate factorial
                }
            });
        }

        // Retrieve results from Future objects
        for (int i = 0; i < futures.length; i++) {
            try {
                Long result = futures[i].get(); // Blocking call to get the result
                System.out.println("Factorial of " + numbers[i] + " is: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown(); // Shutdown the executor service
    }

    // Method to calculate factorial
    private static Long factorial(int number) {
        long result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
    }
}
