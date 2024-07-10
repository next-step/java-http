package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.session.HttpCookie;
import org.apache.http.session.HttpSession;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Cookie extends HttpRequestHeader {
    private static final RequestHeaderName headerName = RequestHeaderName.COOKIE;
    private static final String COOKIE_DELIMITER = "; ";

    private final Set<HttpCookie> cookies = new HashSet<>();

    public Cookie(String cookieValues) {
        var cookies = cookieValues.split(COOKIE_DELIMITER);
        for (String cookie : cookies) {
            this.cookies.add(new HttpCookie(cookie));
        }
    }

    public HttpSession findSession() {
        for (HttpCookie cookie : cookies) {
            var session = cookie.toSession();
            if (session != null) {
                return session;
            }
        }
        return null;
    }

    @Override
    Pair<RequestHeaderName, HttpRequestHeader> getHeader() {
        return Pair.of(headerName, this);
    }

    @Override
    public String toString() {
        return "Cookie" + DELIMITER + cookies.stream().map(Objects::toString).collect(Collectors.joining(COOKIE_DELIMITER));
    }
}
