package ru.job4j.pool;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum = 0;
        private int colSum = 0;

        /* Getter and Setter */
        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i].setRowSum(sums[i].rowSum + matrix[i][j]);
                sums[i].setColSum(sums[i].colSum + matrix[j][i]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (int i = 0; i < sums.length; i++) {
            sums[i] = futures.get(i).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> getTask(int[][] data, int col) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            for (int i = 0; i < data.length; i++) {
                sums.setRowSum(sums.rowSum + data[col][i]);
                sums.setColSum(sums.colSum + data[i][col]);
            }
            return sums;
        });
    }
}
