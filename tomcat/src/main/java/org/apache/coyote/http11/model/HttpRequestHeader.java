package org.apache.coyote.http11.model;

import java.util.Map;

public class HttpRequestHeader {
    public static final String REQUEST_LINE_KEY = "request-line";
    private final Map<String, Object> headers;

    public HttpRequestHeader(final Map<String, Object> headers) {
        this.headers = headers;
    }

    public RequestLine requestLine() {
        return (RequestLine) headerValueBy(REQUEST_LINE_KEY);
    }

    public Object headerValueBy(final String key) {
        return headers.get(key.toLowerCase());
    }

    public int size() {
        return headers.size();
    }
}
