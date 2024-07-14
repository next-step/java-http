package org.apache.coyote.http11;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers = new HashMap<>();

    public HttpHeaders(Map<String, String> value) {
        headers.putAll(value);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String get(String key) {
        return headers.get(key);
    }

    public boolean hasContentLength() {
        return headers.containsKey("Content-Length");
    }

    public int contentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

}
