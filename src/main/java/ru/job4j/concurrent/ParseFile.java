package ru.job4j.concurrent;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        return getText((i) -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getText((i) -> i < 0x80);
    }

    public synchronized String getText(Predicate<Integer> pred) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) != -1) {
                if (pred.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

    public synchronized void saveContent(String content) throws IOException {
        try (OutputStream o = new FileOutputStream(file)) {
            o.write(content.getBytes());
        }
    }
}
