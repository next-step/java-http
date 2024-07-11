package org.apache.coyote.http11;

public record RequestTarget(
        String path,
        QueryParamsMap queryParamsMap,
        Protocol protocol,
        ProtocolVersion protocolVersion
) {

    public static final String REQUEST_PATH_DELIMITER = "\\?";
    public static final String PROTOCOL_DELIMITER = "/";

    public static RequestTarget from(String requestPath, String protocol) {
        String[] parts = requestPath.split(REQUEST_PATH_DELIMITER, 2);
        String queryString = parts.length > 1 ? parts[1] : null;

        String[] protocolInfo = protocol.split(PROTOCOL_DELIMITER);

        return new RequestTarget(
                parts[0],
                QueryParamsMap.from(queryString),
                new Protocol(protocolInfo[0]),
                new ProtocolVersion(protocolInfo[1])
        );
    }
}
