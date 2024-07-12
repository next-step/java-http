package org.apache.coyote.http11;

public record HttpServletRequest(
        HttpMethod httpMethod,
        RequestTarget requestTarget
) {

    public String httpPath() {
        return requestTarget.path();
    }

    public Protocol getProtocol() {
        return requestTarget.protocol();
    }

    public ProtocolVersion getProtocolVersion() {
        return requestTarget.protocolVersion();
    }
}
