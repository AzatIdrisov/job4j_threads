package ru.job4j.net;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private static final long CONST_TIME = 1000;
    private static final int SIZE_BUFFER = 1024;

    private final String url;
    private final String fileName;
    private final int speedLimit;

    public Wget(String url, String fileName, int speedLimit) {
        this.url = url;
        this.fileName = fileName;
        this.speedLimit = speedLimit;     /* speed limit in KB. */
    }

    @Override
    public void run() {
        download();
    }

    private void download() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[SIZE_BUFFER];
            int bytesRead;
            int sumBytes = 0;
            long finishTime;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer)) != -1) {
                sumBytes += bytesRead;
                out.write(dataBuffer, 0, bytesRead);
                if (sumBytes >= speedLimit) {
                    finishTime = System.currentTimeMillis() - startTime;
                    if (finishTime < CONST_TIME) {
                        try {
                            Thread.sleep(finishTime);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    sumBytes = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        String fileName = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, fileName, speed));
        wget.start();
        wget.join();
    }
}
