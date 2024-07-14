package org.apache.coyote.http11;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private Map<String, String> body = new HashMap<>();

    public RequestBody(Map<String, String> body) {
        this.body = body;
    }

    public Map<String, String> map() {
        return body;
    }

    public String get(String key) {
        return body.get(key);
    }
}
