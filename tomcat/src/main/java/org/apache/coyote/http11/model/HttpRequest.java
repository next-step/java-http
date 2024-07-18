package org.apache.coyote.http11.model;

import org.apache.coyote.http11.constants.HttpMethod;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final RequestTarget requestTarget;
    private final HttpHeaders headers;
    private final RequestBody body;
    private final Protocol protocol;
    private HttpCookie cookie;

    public HttpRequest(HttpMethod httpMethod, RequestTarget requestTarget, HttpHeaders headers, RequestBody body, Protocol protocol) {
        this.httpMethod = httpMethod;
        this.requestTarget = requestTarget;
        this.headers = headers;
        this.body = body;
        this.protocol = protocol;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public RequestTarget getRequestTarget() {
        return requestTarget;
    }

    public String getHttpPath() {
        return requestTarget.path();
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getProtocolVersion() {
        return protocol.version();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public HttpCookie getCookie() {
        return cookie;
    }

    public void addCookie(HttpCookie cookie) {
        this.cookie = cookie;
    }

}
