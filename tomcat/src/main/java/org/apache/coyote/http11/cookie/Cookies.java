package org.apache.coyote.http11.cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cookies {
    private final List<Cookie> cookies;

    public Cookies(List<Cookie> cookies) {
        this.cookies = new ArrayList<>(cookies);
    }

    public static Cookies parse(final String cookie) {
        if (cookie == null || cookie.isEmpty()) {
            return new Cookies(new ArrayList<>());
        }
        final String[] cookieParts = cookie.split(";");
        final List<Cookie> cookies = new ArrayList<>();
        for (String cookiePart : cookieParts) {
            final String[] cookieKeyValue = cookiePart.split("=");
            final String key = cookieKeyValue[0].trim();
            final String value = cookieKeyValue[1].trim();
            cookies.add(new Cookie(key, value));
        }
        return new Cookies(cookies);
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

    public String getResponseCookies() {
        String ResponseCookies = cookies.stream()
                .map(cookie -> String.format("%s=%s; ", cookie.getName(), cookie.getValue()))
                .collect(Collectors.joining());
        if (ResponseCookies.isEmpty()) {
            return "";
        }
        return "Set-Cookie: " + ResponseCookies + System.lineSeparator();
    }

    public String getJSessionId() {
        return cookies.stream()
                .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public String getSessionCookie() {
        return cookies.stream()
                .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public void addSessionCookie(String id) {
        cookies.add(new Cookie("JSESSIONID", id));
    }
}
