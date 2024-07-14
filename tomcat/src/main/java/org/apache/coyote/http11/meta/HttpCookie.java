package org.apache.coyote.http11.meta;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookie {

    private static final String COOKIES_DELIMITER = ";";
    private static final String COOKIE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    protected static final int SPLITERATOR_SIZE = 2;
    private static final String JSESSIONID_KEY = "JSESSIONID";

    private final Map<String, String> cookies;

    private HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie from(String cookieHeader) {
        Map<String, String> cookies = new LinkedHashMap<>(parseCookies(cookieHeader));
        return new HttpCookie(cookies);
    }

    public static HttpCookie from() {
        return new HttpCookie(new LinkedHashMap<>());
    }

    public String toCookieHeader() {
        return cookies.entrySet().stream()
            .map(entry -> entry.getKey() + COOKIE_DELIMITER + entry.getValue())
            .collect(Collectors.joining(COOKIES_DELIMITER));
    }

    public boolean hasSessionId() {
        return cookies.containsKey(JSESSIONID_KEY);
    }

    public String getJSessionId() {
        return cookies.get(JSESSIONID_KEY);
    }

    public void addSessionId(String sessionId) {
        cookies.put(JSESSIONID_KEY, sessionId);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    private static Map<String, String> parseCookies(String input) {
        if (input == null || input.isBlank()) {
            return Map.of();
        }

        return Arrays.stream(input.split(COOKIES_DELIMITER))
            .map(entry -> entry.split(COOKIE_DELIMITER, SPLITERATOR_SIZE))
            .collect(Collectors.toMap(cookie -> cookie[KEY_INDEX], cookie -> cookie[VALUE_INDEX],
                (existing, replacement) -> existing, LinkedHashMap::new));
    }
}
