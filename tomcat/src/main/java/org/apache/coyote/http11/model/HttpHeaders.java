package org.apache.coyote.http11.model;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers = new HashMap<>();

    public HttpHeaders(Map<String, String> value) {
        headers.putAll(value);
    }

    public HttpHeaders() {
    }

    public Map<String, String> headers() {
        return headers;
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

    public boolean hasCookie() {
        return headers.containsKey("Cookie");
    }

    public int contentLength() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

}
