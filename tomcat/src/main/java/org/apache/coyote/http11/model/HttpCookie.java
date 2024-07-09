package org.apache.coyote.http11.model;

import org.apache.coyote.http11.StringTokenizer;

import java.util.HashMap;
import java.util.Map;

public class HttpCookie implements HttpHeaderLine {
    private static final String COOKIE_SEPARATOR = "; ";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String JSESSIONID_KEY = "JSESSIONID";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> cookies;

    public HttpCookie(final Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie fromStringLine(final String line) {
        final HashMap<String, String> map = new HashMap<>();

        for (final String cookiePair : StringTokenizer.token(line, COOKIE_SEPARATOR)) {
            final String[] keyValue = StringTokenizer.token(cookiePair, KEY_VALUE_SEPARATOR);
            map.put(keyValue[KEY_INDEX], keyValue[VALUE_INDEX]);
        }

        return new HttpCookie(map);
    }

    public static HttpCookie empty() {
        return new HttpCookie(new HashMap<>());
    }

    public String valueByKey(final String key) {
        return cookies.get(key);
    }

    public void addJSessionId(final String value) {
        cookies.put(JSESSIONID_KEY, value);
    }

    @Override
    public String toLine() {
        final StringBuilder sb = new StringBuilder();

        for (final String key : cookies.keySet()) {
            sb.append(key)
                    .append(KEY_VALUE_SEPARATOR)
                    .append(cookies.get(key))
                    .append(COOKIE_SEPARATOR);
        }

        return sb.substring(0, sb.length() - COOKIE_SEPARATOR.length());
    }
}
