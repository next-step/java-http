package org.apache.coyote.http11.request;

import java.util.List;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;

    public HttpRequest(RequestLine requestLine, HttpRequestHeaders requestHeaders) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
    }

    public HttpRequest(RequestLine requestLine) {
        this(requestLine, new HttpRequestHeaders(List.of()));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpRequestHeaders getRequestHeaders() {
        return requestHeaders;
    }
}
