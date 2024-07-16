package org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.model.ContentType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {
    private final Map<String, String> headers;

    public ResponseHeaders(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public static ResponseHeaders create(HttpRequest httpRequest, ResponseBody body) {
        Map<String, String> headers = new HashMap<>();

        ContentType contentType = ContentType.findByExtension(httpRequest.getExtension());

        headers.put("Content-Type", contentType.getContentType());

        headers.put("Content-Length", body.getContentLength());

        if (httpRequest.hasCookies()) {
            headers.put("Set-Cookie", createHeaderSetCookie(httpRequest.getCookies()));
        }

        return new ResponseHeaders(headers);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    private static String createHeaderSetCookie(Cookies cookies) {
        return cookies.getResponseCookies();
    }
}
