package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestLine {
    private static final String REQUEST_LINE_SPLITERATOR = " ";
    private static final String PATH_QUERY_SPLITERATOR = "&";
    private static final String QUERY_STRING_KEY_VALUE_SPLITERATOR = "=";
    private static final String PROTOCOL_VERSION_SPLITERATOR = "/";

    private final HttpMethod method;
    private final String path;
    private final String protocol;
    private final String version;
    private final Map<String, String> queryStringMap;

    private RequestLine(
            final HttpMethod method,
            final String path,
            final String protocol,
            final String version,
            final Map<String, String> queryStringMap
    ) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.queryStringMap = queryStringMap;
    }

    public static RequestLine from(final String request) {
        String[] requestLines = request.split("\n");
        String[] requestLine = requestLines[0].split(REQUEST_LINE_SPLITERATOR);

        String method = requestLine[0];

        String[] pathAndQueryStrings = requestLine[1].split("\\?");
        String path = pathAndQueryStrings[0];
        Map<String, String> queryStringMap = createQueryStringMap(pathAndQueryStrings);

        String[] protocolAndVersion = requestLine[2].split(PROTOCOL_VERSION_SPLITERATOR);
        String protocol = protocolAndVersion[0];
        String version = protocolAndVersion[1];

        return new RequestLine(HttpMethod.from(method), path, protocol, version, queryStringMap);
    }

    private static Map<String, String> createQueryStringMap(final String[] pathAndQueryStrings) {
        if (pathAndQueryStrings.length <= 1) {
            return Map.of();
        }

        String[] queryStrings = pathAndQueryStrings[1].split(PATH_QUERY_SPLITERATOR);

        return Arrays.stream(queryStrings)
                .map(queryString -> queryString.split(QUERY_STRING_KEY_VALUE_SPLITERATOR))
                .collect(Collectors.toUnmodifiableMap(queryString -> queryString[0], queryString -> queryString[1]));
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getQueryStringMap() {
        return queryStringMap;
    }
}
