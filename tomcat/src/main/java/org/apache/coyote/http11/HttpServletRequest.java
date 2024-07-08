package org.apache.coyote.http11;

public record HttpServletRequest(
        HttpMethod httpMethod,
        HttpPath httpPath,
        Protocol protocol,
        ProtocolVersion protocolVersion
) {
}
