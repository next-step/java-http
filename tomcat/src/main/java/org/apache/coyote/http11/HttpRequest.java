package org.apache.coyote.http11;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final RequestTarget requestTarget;
    private final HttpHeaders headers;

    public HttpRequest(HttpMethod httpMethod, RequestTarget requestTarget, HttpHeaders headers) {
        this.httpMethod = httpMethod;
        this.requestTarget = requestTarget;
        this.headers = headers;
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

}

