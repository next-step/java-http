package org.apache.http.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {
    protected final String id;
    private final Map<String, Object> values = new HashMap<>();

    public HttpSession(final String id) {
        this.id = id;
    }

    public void setAttribute(final String name, final Object value) {
        values.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
