package org.apache.coyote.http11.model;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final Map<String, String> body = new HashMap<>();

    public RequestBody(Map<String, String> value) {
        body.putAll(value);
    }

    public Map<String, String> map() {
        return body;
    }

    public String get(String key) {
        return body.get(key);
    }
}
