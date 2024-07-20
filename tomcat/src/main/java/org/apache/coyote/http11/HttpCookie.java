package org.apache.coyote.http11;

import java.util.*;
import java.util.stream.Collectors;

public class HttpCookie {

    private static final String COOKIE_DELIMITER = "; ";
    public static final String JSESSIONID = "JSESSIONID";
    public static final String KEY_VALUE_DELIMITER = "=";

    private Map<String, String> cookies = new HashMap<>();

    public HttpCookie() {
    }

    public HttpCookie(String line) {
        this.cookies = MapUtils.parseKeyValuePair(KEY_VALUE_DELIMITER, line.split(COOKIE_DELIMITER));
    }

    public static HttpCookie ofSessionId(String sessionId) {
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.addSessionId(UUID.fromString(sessionId));
        return httpCookie;
    }

    public void addSessionId(UUID uuid) {
        cookies.put(JSESSIONID, uuid.toString());
    }

    public String toString() {
        return cookies.entrySet()
                .stream()
                .map(entry -> entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER));
    }

    public boolean contains(String key) {
        return cookies.containsKey(key);
    }

    public String getSessionId() {
        return cookies.get(JSESSIONID);
    }

    public Map<String, String> getMap() {
        return Collections.unmodifiableMap(cookies);
    }

}
