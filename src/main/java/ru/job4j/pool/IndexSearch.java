package ru.job4j.pool;

public class IndexSearch {
    public static int searchIndex(int[] array, int element, int from, int to) {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i] == element) {
                result = i;
                break;
            }
        }
        return result;
    }
}
