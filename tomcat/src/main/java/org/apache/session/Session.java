package org.apache.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Session {
    private final String id;
    private boolean isNew;
    private final Map<String, Object> values = new HashMap<>();

    private Session(final String id, final boolean isNew) {
        this.id = id;
        this.isNew = isNew;
    }

    public static Session from(final String id) {
        return new Session(id, true);
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(final String name) {
        return values.get(name);
    }

    public void addAttribute(final String name, final Object value) {
        values.put(name, value);
    }

    public void removeAttribute(final String name) {
        values.remove(name);
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isNew() {
        return isNew;
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
