package ru.job4j.threadexam.parse;

import ru.job4j.threadexam.Store;
import ru.job4j.threadexam.model.CameraSource;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelParse extends RecursiveTask<Boolean> {
    private final static int MAXSIZE = 2;
    private static Parser parser = new Parser();
    private final List<CameraSource> roots;
    private final int from;
    private final int to;
    private final Store store;

    public ParallelParse(List<CameraSource> roots, int from, int to, Store store) {
        this.roots = roots;
        this.from = from;
        this.to = to;
        this.store = store;
    }

    private void linearParse() {
        for (int index = from; index <= to; ++index) {
            store.add(parser.convertRootToCamera(roots.get(index)).toString());
        }
    }

    @Override
    protected Boolean compute() {
        if (to - from <= MAXSIZE) {
            linearParse();
        } else {
            int mid = (from + to) / 2;
            ParallelParse leftTask = new ParallelParse(roots, from, mid, store);
            ParallelParse rightTask = new ParallelParse(roots, mid + 1, to, store);
            leftTask.fork();
            rightTask.fork();
            leftTask.join();
            rightTask.join();
        }
        return true;
    }

    public static Store parseAndAggregate(String link) {
        List<CameraSource> roots = parser.parseCameraSources(link);
        ForkJoinPool pool = new ForkJoinPool();
        Store store = new Store();
        pool.invoke(new ParallelParse(roots, 0, roots.size() - 1, store));
        return store;
    }

}
