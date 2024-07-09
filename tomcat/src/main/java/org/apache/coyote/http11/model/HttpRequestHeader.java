package org.apache.coyote.http11.model;

import java.util.Map;

public class HttpRequestHeader {
    public static final String REQUEST_LINE_KEY = "request-line";
    private static final String CONTENTS_LENGTH_KEY = "content-length";
    private static final String ZERO = "0";

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

    public boolean hasRequestBody() {
        if (headers.containsKey(CONTENTS_LENGTH_KEY)) {
            return !String.valueOf(headers.get(CONTENTS_LENGTH_KEY)).equals(ZERO);
        }

        return false;
    }
}
