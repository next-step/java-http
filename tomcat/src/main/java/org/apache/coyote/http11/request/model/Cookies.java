package org.apache.coyote.http11.request.model;

import java.util.ArrayList;
import java.util.List;

public class Cookies {
    private final List<Cookie> cookies;

    public Cookies(List<Cookie> cookies) {
        this.cookies = new ArrayList<>(cookies);
    }

    public boolean hasJSessionId() {
        return cookies.stream()
                .anyMatch(cookie -> "JSESSIONID".equals(cookie.getName()));
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public static Cookies emptyCookies() {
        return new Cookies(new ArrayList<>());
    }

    public String getJSessionId() {
        return cookies.stream()
                .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public String getResponseCookies() {
        return cookies.stream()
                .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                .reduce((cookie1, cookie2) -> cookie1 + "; " + cookie2)
                .orElse("");
    }
}
