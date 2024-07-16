package org.apache.coyote.http11.request;

import java.util.List;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, HttpRequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public HttpRequest(RequestLine requestLine, HttpRequestHeaders requestHeaders) {
        this(requestLine, requestHeaders, null);
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

    public RequestBody getRequestBody() {
        return requestBody;
    }
}
