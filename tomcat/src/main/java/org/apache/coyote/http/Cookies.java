package org.apache.coyote.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookies {

    private static final String JSESSIONID = "JSESSIONID";

    private final Map<String, Cookie> cookies = new HashMap<>();

    public void addCookie(final Cookie... cookie) {
        addCookies(Arrays.asList(cookie));
    }

    public void addCookies(final List<Cookie> cookies) {
        cookies.forEach(cookie -> this.cookies.put(cookie.getName(), cookie));
    }

    public Cookie getCookie(final String cookieName) {
        return this.cookies.get(cookieName);
    }

    public Cookie getSessionCookie() {
        return getCookie(JSESSIONID);
    }
}
