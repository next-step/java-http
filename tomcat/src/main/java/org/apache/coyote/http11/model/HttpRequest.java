package org.apache.coyote.http11.model;

import org.apache.coyote.http11.constants.HttpMethod;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final RequestTarget requestTarget;
    private final HttpHeaders headers;
    private final RequestBody body;

    public HttpRequest(HttpMethod httpMethod, RequestTarget requestTarget, HttpHeaders headers, RequestBody body) {
        this.httpMethod = httpMethod;
        this.requestTarget = requestTarget;
        this.headers = headers;
        this.body = body;
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
        return requestTarget.protocol();
    }

    public ProtocolVersion getProtocolVersion() {
        return requestTarget.protocolVersion();
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

}

