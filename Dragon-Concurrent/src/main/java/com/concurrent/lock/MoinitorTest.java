package com.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

public class MoinitorTest {
    private final static Object lockHelper = new Object();
//ReentrantLock
    public static void main(String[] args) {
        System.out.println("Hello World");
        synchronized (lockHelper) {
            System.out.println("insert Syn...");
        }
    }
}
