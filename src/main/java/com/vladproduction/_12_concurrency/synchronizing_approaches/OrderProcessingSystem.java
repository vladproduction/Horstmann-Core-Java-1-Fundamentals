package com.vladproduction._12_concurrency.synchronizing_approaches;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture concept;
 *
 * Simulate a simple Order-Processing-System where we:
 * 1.Place an order.
 * 2.Check the inventory for the products ordered.
 * 3.Process the payment.
 * 4.Ship the order.
 * All these operations will be done asynchronously using CompletableFuture.
 * This will allow us to carry out checks and updates concurrently, improving the efficiency of the order processing system.
 * */
public class OrderProcessingSystem {

    public static void main(String[] args) {
        
        // Place an order
        CompletableFuture<Integer> orderFuture = placeOrder();
        
        // Check inventory
        CompletableFuture<Boolean> inventoryCheckedFuture = orderFuture.thenCompose(orderId -> 
            checkInventory(orderId)
        );

        // Process payment only if inventory is available
        CompletableFuture<Boolean> paymentProcessedFuture = inventoryCheckedFuture.thenCompose(inStock -> {
            if (inStock) {
                return processPayment();
            } else {
                return CompletableFuture.completedFuture(false); // Payment not processed if out of stock
            }
        });

        // Ship the order only if payment was successful
        CompletableFuture<Void> shippingFuture = paymentProcessedFuture.thenAccept(isProcessed -> {
            if (isProcessed) {
                shipOrder();
            } else {
                System.out.println("Payment was not processed, order cannot be shipped.");
            }
        });

        // Wait for all tasks to complete
        try {
            shippingFuture.get(); // This will block until the order has been processed
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static CompletableFuture<Integer> placeOrder() {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate order placement
            System.out.println("Placing order...");
            sleep(1000); // Simulate the time taken to place an order
            int orderId = 123; // Simulated order ID
            System.out.println("Order placed with ID: " + orderId);
            return orderId;
        });
    }

    private static CompletableFuture<Boolean> checkInventory(int orderId) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate inventory checking
            System.out.println("Checking inventory for order ID: " + orderId);
            sleep(1000); // Simulate time taken for inventory check
            boolean inStock = true; // Simulated stock availability
            System.out.println("Inventory checked: " + (inStock ? "Available" : "Out of Stock"));
            return inStock;
        });
    }

    private static CompletableFuture<Boolean> processPayment() {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate payment processing
            System.out.println("Processing payment...");
            sleep(1000); // Simulate time taken to process payment
            boolean paymentSuccessful = true; // Simulated payment success
            System.out.println("Payment processed: " + paymentSuccessful);
            return paymentSuccessful;
        });
    }

    private static void shipOrder() {
        // Simulate shipping the order
        System.out.println("Shipping the order...");
        sleep(1000); // Simulate time taken to ship the order
        System.out.println("Order shipped!");
    }

    // Helper method to simulate time delay
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
