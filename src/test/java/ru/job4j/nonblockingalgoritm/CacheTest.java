package ru.job4j.nonblockingalgoritm;

import org.junit.Test;

public class CacheTest {

    @Test (expected = OptimisticException.class)
    public void whenTryChangeOneModel() throws InterruptedException {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        cache.update(new Base(1, 1));
        cache.update(new Base(1, 3));
        Thread first = new Thread(() -> cache.update(new Base(1, 1)));
        first.start();
        first.join();
        Thread second = new Thread(() -> cache.update(new Base(1, 5)));
        second.start();
        second.join();
    }
}
