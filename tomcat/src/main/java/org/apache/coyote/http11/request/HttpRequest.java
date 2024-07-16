package org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.model.*;

public class HttpRequest {
    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final RequestBodies requestBodies;
    private final Cookies cookies;

    public HttpRequest(RequestLine requestLine, RequestHeaders requestHeaders, RequestBodies requestBodies, Cookies cookies) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestBodies = requestBodies;
        this.cookies = cookies;
    }

    public Path getPath() {
        return requestLine.getPath();
    }

    public RequestBodies getRequestBodies() {
        return requestBodies;
    }

    public HttpMethod getHttpMethod() {
        return requestLine.getHttpMethod();
    }

    public Cookies getCookies() {
        return cookies;
    }
}
