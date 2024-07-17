package org.apache.coyote.http11.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSession {
    private static final String INVALIDATE = "invalidate";

    private final String id;
    private final Map<String, Object> values = new HashMap<>();

    public HttpSession() {
        this.id = UUID.randomUUID().toString();
        this.values.put(INVALIDATE, false);
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(final String key) {
        return values.get(key);
    }

    public void setAttribute(final String name, final Object value) {
        values.put(name, value);
    }

    public void removeAttribute(final String key) {
        values.remove(key);
    }

    public void invalidate() {
        values.put(INVALIDATE, true);
    }

}
