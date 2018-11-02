package com.yyq.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AbstractQueuedSynchonizer
 *
 * @author yyq
 * @since 2018/10/29
 */
public class AbstractQueuedSynchonizerDemo {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread myThread1 = new MyThread("t1", lock);
        Thread myThread2 = new MyThread("t2", lock);
        myThread1.start();
        myThread2.start();

    }
}

class MyThread extends Thread {
    private Lock lock;

    public MyThread(String name, Lock lock) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread() + " running");
        } finally {
            lock.unlock();
        }
    }
}
