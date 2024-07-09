package org.apache.coyote.http11.model;

import java.util.Map;

public class HttpRequestHeader {
    public static final String REQUEST_LINE_KEY = "request-line";
    private static final String CONTENTS_LENGTH_KEY = "Content-Length";
    private static final String COOKIE_KEY = "Cookie";
    private static final int ZERO = 0;

    private final Map<String, Object> headers;

    public HttpRequestHeader(final Map<String, Object> headers) {
        this.headers = headers;
    }

    public RequestLine requestLine() {
        return (RequestLine) headerValueBy(REQUEST_LINE_KEY);
    }

    public Object headerValueBy(final String key) {
        return headers.get(key);
    }

    public int size() {
        return headers.size();
    }

    public boolean hasRequestBody() {
        if (headers.containsKey(CONTENTS_LENGTH_KEY)) {
            return contentLength() != ZERO;
        }

        return false;
    }

    public int contentLength() {
        final String contentLength = String.valueOf(headers.get(CONTENTS_LENGTH_KEY));

        if (contentLength == null) {
            return 0;
        }

        return Integer.parseInt(contentLength);
    }

    public boolean hasCookie() {
        return headers.containsKey(COOKIE_KEY);
    }

    public HttpCookie httpCookie() {
        return (HttpCookie) headers.get(COOKIE_KEY);
    }
}
