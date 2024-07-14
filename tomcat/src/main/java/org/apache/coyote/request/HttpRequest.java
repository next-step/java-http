package org.apache.coyote.request;

import java.util.Optional;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public String getHttpPath() {
        return requestLine.getHttpPath();
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public Optional<String> findCookie() {
        return requestHeaders.findCookie();
    }
}
