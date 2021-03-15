package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        users.put(user.getId(), user);
        return users.contains(user);
    }

    public synchronized boolean update(User user) {
        if (!users.containsKey(user.getId())) {
            return false;
        }
        users.put(user.getId(), user);
        return true;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User fromUser;
        User toUser;
        if (!users.containsKey(fromId) || !users.containsKey(toId)) {
            return false;
        } else {
            fromUser = users.get(fromId);
            toUser = users.get(toId);
        }
        if (fromUser.getAmount() < amount) {
            return false;
        }
        fromUser.setAmount(fromUser.getAmount() - amount);
        toUser.setAmount(toUser.getAmount() + amount);
        return true;
    }

}
