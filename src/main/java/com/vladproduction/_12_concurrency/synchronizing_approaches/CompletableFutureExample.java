package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            // Long computation or I/O
            return "Hello";
        }).thenAccept(result -> System.out.println(result + " World!")); // Chained completion
    }
}
