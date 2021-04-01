package ru.job4j.multithreads;

/**
 * Implement sequentially work of threads
 *
 * @author Azat Idrisov
 * @version 1
 */

public class MasterSlaveBarrier {

    private volatile boolean flag = false;

    public synchronized void tryMaster() {
        doneMaster();
        System.out.println("Thread A");
        this.notify();
        try {
            this.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void trySlave() {
        if (flag) {
            System.out.println("Thread B");
            doneSlave();
            this.notify();
        }
        try {
            this.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void doneMaster() {
        flag = true;
    }

    public void doneSlave() {
        flag = false;
    }
}
