package org.apache.http.header;

import org.apache.http.cookie.HttpCookie;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SetCookie implements HttpResponseHeader {
    private final Set<HttpCookie> cookies;
    private static final String COOKIE_DELIMITER = "; ";

    public SetCookie() {
        this.cookies = new HashSet<>();
    }


    SetCookie(Set<HttpCookie> cookies) {
        this.cookies = cookies;
    }


    public SetCookie addCookie(HttpCookie cookie) {
        final Set<HttpCookie> newCookies = new HashSet<>(cookies);
        newCookies.add(cookie);
        return new SetCookie(newCookies);
    }


    @Override
    public String toString() {
        return "Set-Cookie" + DELIMITER + cookies.stream().map(Objects::toString).collect(Collectors.joining(COOKIE_DELIMITER));
    }
}
