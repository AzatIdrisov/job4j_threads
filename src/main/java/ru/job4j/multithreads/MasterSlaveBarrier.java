package ru.job4j.multithreads;

/**
 * Implement sequentially work of threads
 *
 * @author Azat Idrisov
 * @version 2
 */

public class MasterSlaveBarrier {
    private boolean flag = false;

    public synchronized void tryMaster() {
        while (flag) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread A");
    }

    public synchronized void trySlave() {
        while (!flag) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread B");
    }

    public synchronized void doneMaster() {
        flag = true;
        notify();
    }

    public synchronized void doneSlave() {
        flag = false;
        notify();
    }
}
