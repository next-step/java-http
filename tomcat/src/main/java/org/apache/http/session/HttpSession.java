package org.apache.http.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    protected final String id;
    private final Map<String, Object> values = new HashMap<>();

    public HttpSession(final String id) {
        this.id = id;
    }

    public void setAttribute(final String name, final Object value) {
        values.put(name, value);
    }

}
