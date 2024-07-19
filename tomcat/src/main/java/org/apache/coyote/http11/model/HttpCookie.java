package org.apache.coyote.http11.model;

import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    private final Map<String, String> cookies = new HashMap<>();

    public HttpCookie(Map<String, String> value) {
        cookies.putAll(value);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    public boolean hasJSessionId() {
        return cookies.containsKey("JSESSIONID");
    }

    public String JSessionId() {
        return cookies.get("JSESSIONID");
    }

}
