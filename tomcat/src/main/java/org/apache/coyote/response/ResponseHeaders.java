package org.apache.coyote.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {

    private final Map<String, String> headers;

    public ResponseHeaders() {
        this.headers = new HashMap<>();
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public String get(String key) {
        return headers.get(key);
    }
}
