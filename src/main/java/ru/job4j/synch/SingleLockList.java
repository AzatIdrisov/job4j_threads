package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final SimpleArray<T> list = new SimpleArray();

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    private Iterable<T> copy(SimpleArray<T> original) {
        return new Iterable<T>() {

            private final Object[] data = new Object[original.getSize()];

            private int cursor;

            {
                for (int i = 0; i < original.getSize(); i++) {
                    data[i] = original.get(i);
                }
            }

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return cursor != data.length;
                    }

                    @Override
                    public T next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return (T) data[cursor++];
                    }
                };
            }
        };
    }
}
