package ru.job4j.pool;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.block.Consumer;
import ru.job4j.block.Producer;
import ru.job4j.block.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    private static final int SIZE = 500;
    private int[][] matrix = new int[SIZE][SIZE];

    @Before
    public void init() {
        int count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = count;
                count++;
            }
        }
    }

    @Test
    public void testSync() {
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        assertThat(sums[0].getRowSum(), is(125250));
        assertThat(sums[0].getColSum(), is(62375500));
    }

    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        assertThat(sums[0].getRowSum(), is(125250));
        assertThat(sums[0].getColSum(), is(62375500));
    }

    @Test
    public void checkAsync() throws ExecutionException, InterruptedException {
        Long timeSync = new Date().getTime();
        RolColSum.Sums[] sumsSync = RolColSum.sum(matrix);
        timeSync = new Date().getTime() - timeSync;
        Long timeAsync = new Date().getTime();
        RolColSum.Sums[] sumsAsync = RolColSum.sum(matrix);
        timeAsync = new Date().getTime() - timeAsync;
        System.out.println("Time for sycn: " + timeSync);
        System.out.println("Time for asycn: " + timeAsync);
    }
}