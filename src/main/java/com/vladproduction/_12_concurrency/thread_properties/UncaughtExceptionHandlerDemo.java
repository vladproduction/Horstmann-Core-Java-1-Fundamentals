package com.vladproduction._12_concurrency.thread_properties;

/**
 * when a thread throws an unchecked exception that is not caught within the run method, it terminates the thread.
 * To handle these uncaught exceptions, you can set an UncaughtExceptionHandler for the thread or for all threads created by a thread group.
 * */
public class UncaughtExceptionHandlerDemo {

    public static void main(String[] args) {

        // Create a thread with a custom uncaught exception handler
        Thread thread = new Thread(new TaskWithException2(), "My-Thread");

        //set a custom uncaught exception handler (CustomExceptionHandler) for the thread to handle any uncaught exceptions.
        thread.setUncaughtExceptionHandler(new CustomExceptionHandler());

        // Start the thread
        thread.start();

        //output:
        //Thread My-Thread is running.
        //Uncaught exception in thread My-Thread : An unchecked exception occurred!
    }
}

class TaskWithException implements Runnable {
    @Override
    public void run() {

        System.out.println("Thread " + Thread.currentThread().getName() + " is running.");

        //run method throws a RuntimeException, which is an unchecked exception.
        throw new RuntimeException("An unchecked exception occurred!");
    }
}

class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

    // The uncaughtException method receives the thread and the throwable (exception) that was uncaught.
    // It prints an error message indicating which thread encountered the exception.
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Uncaught exception in thread " + t.getName() + " : " + e.getMessage());
    }
}
