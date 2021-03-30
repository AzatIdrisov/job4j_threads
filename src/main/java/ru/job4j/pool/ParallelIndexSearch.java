package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch extends RecursiveTask<Integer> {

    private static final int MAXSIZE = 10;

    private final int[] array;
    private final int element;
    private final int beginIndex;

    public ParallelIndexSearch(int[] array, int element, int beginIndex) {
        this.array = array;
        this.element = element;
        this.beginIndex = beginIndex;
    }

    @Override
    protected Integer compute() {
        int result;
        if (array.length <= MAXSIZE) {
            result = IndexSearch.searchIndex(array, element);
            return result != -1 ? result + beginIndex : -1;
        } else {
            int mid = array.length / 2;
            int[] left = new int[mid];
            int[] right = new int[mid];
            System.arraycopy(array, 0, left, 0, left.length);
            System.arraycopy(array, left.length, right, 0, right.length);
            ParallelIndexSearch leftSide = new ParallelIndexSearch(
                    left,
                    this.element,
                    beginIndex);
            ParallelIndexSearch rightSide = new ParallelIndexSearch(
                    right,
                    this.element,
                    beginIndex + mid);
            leftSide.fork();
            rightSide.fork();
            int leftResult = leftSide.join();
            int rightResult = rightSide.join();
            if (leftResult != -1) {
                return leftResult;
            }
            if (rightResult != -1) {
                return rightResult;
            }
            return -1;
        }
    }

    public static int indexSearch(int[] array, int element, int beginIndex) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch(array, element, beginIndex));
    }

    public static void main(String[] args) {
        int[] source = new int[1000];
        for (int i = 0; i < 1000; i++) {
            source[i] = i;
        }
        System.out.println(ParallelIndexSearch.indexSearch(source, 750, 0));
    }

}
