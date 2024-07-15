package org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.cookie.Cookies;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {
    private final Map<String, String> headers;

    public ResponseHeaders(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public static ResponseHeaders create(HttpRequest httpRequest) {
        Map<String, String> headers = new HashMap<>();
        if (httpRequest.hasCookies()) {
            headers.put("Set-Cookie", createHeaderSetCookie(httpRequest.getCookies()));
        }
        return new ResponseHeaders(headers);
    }

    private static String createHeaderSetCookie(Cookies cookies) {
        return cookies.getResponseCookies();
    }
}
