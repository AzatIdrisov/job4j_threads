package ru.job4j.nonblockingalgoritm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        memory.computeIfPresent(model.getId(), (key, value) -> {
            int version = model.getVersion();
            if (version != value.getVersion()) {
                throw new OptimisticException("Versions are not same");
            }
            version++;
            model.setVersion(version);
            return model;
        });
        return true;
    }

    public void delete(Base model) {
        memory.remove(model);
    }
}
