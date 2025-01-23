package com.vladproduction._12_concurrency.thread_properties;

public class ExceptionHandlingThread extends Thread {
    @Override
    public void run() {
        try {
            // This will cause an ArithmeticException
            int result = 10 / 0;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println(getName() + " encountered an exception: " + e.getMessage());
        } finally {
            System.out.println(getName() + " has finished execution after an exception.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExceptionHandlingThread thread = new ExceptionHandlingThread();
        thread.start();
        thread.join(); // Wait for the thread to finish
    }
}
