package org.example.domain;

import java.util.List;

public class Signal {
    private final String type;
    private final User from;
    private final List<User> to;
    private final Object data;

    public Signal(String type, User from, List<User> to, Object data) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.data = data;
    }

    public Signal(String type, User from, List<User> to) {
        this(type, from, to, null);
    }

    public Signal(String type) {
        this(type, null, null, null);
    }

    public String getType() {
        return type;
    }

    public User getFrom() {
        return from;
    }

    public List<User> getTo() {
        return to;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "type='" + type + '\'' +
                ", from=" + (from != null ? from.getUsername() : "null") +
                ", data=" + (data != null ? data.getClass().getSimpleName() : "null") +
                '}';
    }
}