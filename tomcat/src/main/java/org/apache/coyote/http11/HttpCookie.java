package org.apache.coyote.http11;

import org.apache.coyote.http11.constants.HttpCookies;

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
        this.cookies = toMap(line);
    }

    public static HttpCookie ofSessionId(String sessionId) {
        HttpCookie httpCookie = new HttpCookie();
        httpCookie.addSessionId(UUID.fromString(sessionId));
        return httpCookie;
    }

    public void addSessionId(UUID uuid) {
        cookies.put(JSESSIONID, uuid.toString());
    }

    public String toValue() {
        return cookies.entrySet()
                .stream()
                .map(entry -> entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_DELIMITER));
    }

    public boolean contains(String key) {
        return cookies.containsKey(key);
    }

    public String getSessionId() {
        return cookies.get(HttpCookies.JSESSIONID);
    }

    public Map<String, String> getMap() {
        return Collections.unmodifiableMap(cookies);
    }

    private Map<String, String> toMap(String line) {
        return Arrays.stream(line.split(COOKIE_DELIMITER))
                .map(cookie -> cookie.split("="))
                .map(cookie -> Map.entry(cookie[0], cookie[1]))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
