package ru.job4j.block;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int size;
    private final Object monitor = this;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public synchronized void offer(T value) {
        while (queue.size() >= this.size) {
            try {
                monitor.wait();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        monitor.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            monitor.wait();
        }
        T element = queue.poll();
        monitor.notifyAll();
        return element;
    }

    public synchronized boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
