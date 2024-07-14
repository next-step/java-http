package org.apache.coyote.http11;

public class HttpRequest {
    private HttpMethod httpMethod;
    private RequestTarget requestTarget;

    public HttpRequest(HttpMethod httpMethod, RequestTarget requestTarget) {
        this.httpMethod = httpMethod;
        this.requestTarget = requestTarget;
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

}

