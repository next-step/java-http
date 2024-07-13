package org.apache.coyote.http;

import java.util.*;
import java.util.stream.Collectors;

public class Cookies {

    private static final String JSESSIONID = "JSESSIONID";

    private final Map<String, Cookie> cookies = new HashMap<>();

    public void addCookie(final Cookie... cookie) {
        final Map<String, Cookie> cookieMapping = Arrays.stream(cookie).collect(Collectors.toMap(Cookie::getName, it -> it));
        this.cookies.putAll(cookieMapping);
    }

    public Cookie getCookie(final String cookieName) {
        return this.cookies.get(cookieName);
    }

    public Cookie getSessionCookie() {
        return getCookie(JSESSIONID);
    }
}
