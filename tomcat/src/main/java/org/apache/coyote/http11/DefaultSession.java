package org.apache.coyote.http11;

import org.apache.catalina.Manager;
import org.apache.catalina.Session;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSession implements Session {
    private final String id;
    private final Manager sessionManager;
    private final Map<String, Object> values = new ConcurrentHashMap<>();

    private DefaultSession(final String id, final Manager sessionManager) {
        this.id = id;
        this.sessionManager = sessionManager;
        sessionManager.add(this);
    }

    public static Session of(final String id, final Manager manager) {
        return new DefaultSession(id, manager);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Object getAttribute(final String name) {
        return values.get(name);
    }

    @Override
    public void setAttribute(final String name, final Object value) {
        values.put(name, value);
    }

    @Override
    public void removeAttribute(final String name) {
        values.remove(name);
    }

    @Override
    public void invalidate() {
        values.clear();
        sessionManager.remove(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DefaultSession that = (DefaultSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
