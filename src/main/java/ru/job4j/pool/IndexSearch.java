package ru.job4j.pool;

public class IndexSearch {
    public static int searchIndex(int[] array, int element) {
        int result = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                result = i;
                break;
            }
        }
        return result;
    }
}
