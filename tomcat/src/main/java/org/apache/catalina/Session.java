package org.apache.catalina;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
    private final String id;
    private final Manager sessionManager;
    private final Map<String, Object> values = new ConcurrentHashMap<>();

    private Session(final String id, final Manager sessionManager) {
        this.id = id;
        this.sessionManager = sessionManager;
        sessionManager.add(this);
    }

    public static Session of(final String id, final Manager manager) {
        return new Session(id, manager);
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(final String name) {
        return values.get(name);
    }

    public void setAttribute(final String name, final Object value) {
        values.put(name, value);
    }

    public void removeAttribute(final String name) {
        values.remove(name);
    }

    public void invalidate() {
        values.clear();
        sessionManager.remove(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Session that = (Session) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
