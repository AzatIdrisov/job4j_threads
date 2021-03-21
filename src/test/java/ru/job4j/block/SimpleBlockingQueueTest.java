package ru.job4j.block;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class SimpleBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        List<Integer> listIn = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> listOut = new ArrayList<>();
        Producer producer = new Producer(queue, listIn);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Consumer<Integer> consumer = new Consumer<>(queue, listOut);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        producerThread.join();
        consumerThread.interrupt();
        consumerThread.join();
        assertThat(listOut, is(listIn));
    }
}
