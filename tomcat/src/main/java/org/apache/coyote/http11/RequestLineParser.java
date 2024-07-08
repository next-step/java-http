package org.apache.coyote.http11;

import org.apache.coyote.Parser;

public class RequestLineParser implements Parser {

    public static final String SPACE = " ";
    public static final String PROTOCOL_DELIMITER = "/";

    @Override
    public HttpServletRequest parse(String requestLine) {
        String[] parts = requestLine.split(SPACE);
        final HttpMethod httpMethod = HttpMethod.from(parts[0]);
        final HttpPath httpPath = HttpPath.from(parts[1]);

        String[] protocolInfo = parts[2].split(PROTOCOL_DELIMITER);
        final Protocol protocol1 = new Protocol(protocolInfo[0]);
        final ProtocolVersion protocolVersion = new ProtocolVersion(protocolInfo[1]);

        return new HttpServletRequest(httpMethod, httpPath, protocol1, protocolVersion);
    }
}
