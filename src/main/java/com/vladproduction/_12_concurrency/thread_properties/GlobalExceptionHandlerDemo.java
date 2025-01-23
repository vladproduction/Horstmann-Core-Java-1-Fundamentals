package com.vladproduction._12_concurrency.thread_properties;

/**
 * This class sets a global default uncaught exception handler (GlobalExceptionHandler) that will apply to all threads that do not have their own handlers.
 */
public class GlobalExceptionHandlerDemo {

    public static void main(String[] args) {

        // sets a global default uncaught exception handler (GlobalExceptionHandler)
        // that will apply to all threads that do not have their own handlers
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());

        // Start multiple threads that will throw exceptions
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new TaskWithException2(), "Thread-" + (i + 1));
            thread.start();
        }
    }
}

class TaskWithException2 implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getName() + " is running.");
        throw new RuntimeException("An unchecked exception occurred!"); //this runnable throws a RuntimeException.
    }
}

/**
 * This class implements the UncaughtExceptionHandler interface and handles uncaught exceptions for any thread
 * executing a task that throws an unchecked exception.
 */
class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Global handler: Uncaught exception in thread " + t.getName() + " : " + e.getMessage());
    }
}
