package org.apache.coyote.http11.request.model;

import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.parser.CookiesParser;

import java.util.Map;
import java.util.Objects;

public record RequestHeaders(Map<String, Object> requestHeaders) {

    public int getContentLength() {
        return Integer.parseInt(String.valueOf(requestHeaders.get("Content-Length")).trim());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHeaders that = (RequestHeaders) o;
        return Objects.equals(requestHeaders, that.requestHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(requestHeaders);
    }
}
