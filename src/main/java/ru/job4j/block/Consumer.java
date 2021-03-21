package ru.job4j.block;

import java.util.List;

public class Consumer<T> implements Runnable {
    private SimpleBlockingQueue<T> queue;
    private List<T> list;

    public Consumer(SimpleBlockingQueue<T> queue, List<T> list) {
        this.queue = queue;
        this.list = list;
    }

    public void run() {
        while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
            try {
                T element = queue.poll();
                System.out.println("Consumer try to poll data " + element);
                list.add(element);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
