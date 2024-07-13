package org.apache.http.header;

import org.apache.http.session.HttpCookie;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class SetCookie implements HttpResponseHeader {
    private static final String NAME = "Set-Cookie";
    private static final String COOKIE_DELIMITER = "; ";

    private final Set<HttpCookie> cookies;

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
        return NAME + DELIMITER + cookies.stream().map(Objects::toString).collect(Collectors.joining(COOKIE_DELIMITER));
    }
}
