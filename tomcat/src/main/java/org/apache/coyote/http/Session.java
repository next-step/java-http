package org.apache.coyote.http;

import java.util.HashMap;
import java.util.Map;

public class Session {

    private final String id;
    private final Map<String, Object> attributes = new HashMap<>();

    public Session(final String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setAttribute(final String name, final Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(final String name) {
        return this.attributes.get(name);
    }
}
