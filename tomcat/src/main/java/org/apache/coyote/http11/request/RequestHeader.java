package org.apache.coyote.http11.request;

import java.util.Map;

public class RequestHeader {
    private final RequestLine requestLine;
    private final Map<String, Object> headers;

    public RequestHeader( RequestLine requestLine, Map<String, Object> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }
}
