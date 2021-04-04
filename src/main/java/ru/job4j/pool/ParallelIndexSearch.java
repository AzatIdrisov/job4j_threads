package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch extends RecursiveTask<Integer> {

    private static final int MAXSIZE = 10;

    private final int[] array;
    private final int element;
    private final int from;
    private final int to;

    public ParallelIndexSearch(int[] array, int element) {
        this.array = array;
        this.element = element;
        this.from = 0;
        this.to = array.length - 1;
    }

    public ParallelIndexSearch(int[] array, int element, int from, int to) {
        this.array = array;
        this.element = element;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        int result;
        if (from - to <= MAXSIZE) {
            result = IndexSearch.searchIndex(array, element, from, to);
            return result;
        } else {
            int mid = array.length / 2;
            ParallelIndexSearch leftSide = new ParallelIndexSearch(
                    this.array,
                    this.element,
                    from,
                    mid);
            ParallelIndexSearch rightSide = new ParallelIndexSearch(
                    this.array,
                    this.element,
                    mid + 1,
                    to);
            leftSide.fork();
            rightSide.fork();
            int leftResult = leftSide.join();
            int rightResult = rightSide.join();
            if (leftResult != -1) {
                return leftResult;
            }
            return rightResult;
        }
    }

    public static int indexSearch(int[] array, int element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch(array, element));
    }

    public static void main(String[] args) {
        int[] source = new int[1000];
        for (int i = 0; i < 1000; i++) {
            source[i] = i;
        }
        System.out.println(ParallelIndexSearch.indexSearch(source, 750));
    }

}
