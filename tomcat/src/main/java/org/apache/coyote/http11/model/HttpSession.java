package org.apache.coyote.http11.model;

import org.apache.coyote.http11.session.Session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession implements Session {
    private static final String INVALIDATE_KEY = "invalidate";

    private final String id;
    private final Map<String, Object> values = new HashMap<>();

    public HttpSession(final String id) {
        this.id = id;
        this.values.put(INVALIDATE_KEY, false);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Object getAttribute(String name) {
        checkIfValid();
        return values.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        checkIfValid();
        values.put(name, value);
    }

    @Override
    public void removeAttribute(String name) {
        checkIfValid();
        values.remove(name);
    }

    @Override
    public void invalidate() {
        values.put(INVALIDATE_KEY, true);
    }

    public void checkIfValid() {
        if (!(boolean) values.get(INVALIDATE_KEY)) {
            throw new IllegalStateException("Session is invalidated");
        }
    }
}
