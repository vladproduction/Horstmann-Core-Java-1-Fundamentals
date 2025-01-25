package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Callable<Integer> task = () -> {
            return 42; // Return a value
        };

        Future<Integer> future = executor.submit(task);
        Integer result = future.get(); // Get the result

        System.out.println("Result: " + result);
        executor.shutdown();
    }
}
