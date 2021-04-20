package ru.job4j.threadexam.model;

public class Token {

    private final String value;
    private final int ttl;

    public Token(String value, int ttl) {
        this.value = value;
        this.ttl = ttl;
    }

    public String getValue() {
        return value;
    }

    public int getTtl() {
        return ttl;
    }

    @Override
    public String toString() {
        return "Token{"
                + "value='" + value + '\''
                + ", ttl=" + ttl
                + '}';
    }
}
