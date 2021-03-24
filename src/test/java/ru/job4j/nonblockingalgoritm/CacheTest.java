package ru.job4j.nonblockingalgoritm;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.concurrent.atomic.AtomicReference;

public class CacheTest {

    @Test
    public void whenTryChangeOneModel() throws InterruptedException {
        AtomicReference<Exception> exception = new AtomicReference<>();
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        Thread thread = new Thread(() -> cache.update(new Base(1, 1)));
        thread.start();
        thread.join();
        thread = new Thread(() -> {
            try {
                cache.update(new Base(1, 2));
            } catch (OptimisticException e) {
                exception.set(e);
            }
        });
        thread.start();
        thread.join();
        assertThat(exception.get().getMessage(), is("Versions are not same"));
    }
}