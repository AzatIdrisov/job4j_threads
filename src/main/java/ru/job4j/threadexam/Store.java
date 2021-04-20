package ru.job4j.threadexam;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class Store {

    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    public void add(String json) {
        queue.offer(json);
    }

    @Override
    public String toString() {
        return queue.stream().collect(Collectors.joining("," + System.lineSeparator(), "["
                + System.lineSeparator(), System.lineSeparator() + "]"));
    }
}
