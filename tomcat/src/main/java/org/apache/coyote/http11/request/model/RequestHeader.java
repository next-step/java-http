package org.apache.coyote.http11.request.model;

import java.util.Map;

public class RequestHeader {
    private final RequestLine requestLine;
    private final Map<String, Object> headers;
    private final RequestBodies requestBodies;

    public RequestHeader( RequestLine requestLine, Map<String, Object> headers, RequestBodies requestBodies) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBodies = requestBodies;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestBodies getRequestBodies() {
        return requestBodies;
    }
}
