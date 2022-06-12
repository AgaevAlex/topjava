package ru.javawebinar.topjava.util;

import java.util.concurrent.locks.ReentrantLock;

public class IdGenerator {
    static int counter = 0; // a global counter
    static ReentrantLock counterLock = new ReentrantLock(true);

    public static int inc() {
        counterLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ": " + counter);
            counter++;
        } finally {
            counterLock.unlock();
        }
        return counter;
    }
}
