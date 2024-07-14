package org.apache.coyote.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Session {

    private final String id;
    private final Map<String, Object> values = new HashMap<>();

    public Session() {
        this(UUID.randomUUID().toString());
    }

    public Session(String id) {
        this.id = id;
    }

    public Object getAttribute(String key) {
        return values.get(key);
    }

    public void setAttribute(String key, Object value) {
        values.put(key, value);
    }

    public String getId() {
        return id;
    }
}
