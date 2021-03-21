package ru.job4j.block;

import java.util.List;

public class Producer<T> implements Runnable {
    private SimpleBlockingQueue<T> queue;
    private List<T> list;

    public Producer(SimpleBlockingQueue<T> queue, List<T> list) {
        this.queue = queue;
        this.list = list;
    }

    public void run() {
        for (T t : list) {
            System.out.println("Producer try insert data to queue " + t);
            try {
                queue.offer(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
