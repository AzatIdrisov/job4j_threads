package ru.job4j.pool;

import ru.job4j.block.Consumer;
import ru.job4j.block.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int size) {
        tasks = new SimpleBlockingQueue<>(size);
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll().run();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }));
            threads.forEach(Thread::start);
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

}
