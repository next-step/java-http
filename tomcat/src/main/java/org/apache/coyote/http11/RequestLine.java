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

    // 요청 타겟
    private final String path;
    private final Map<String, String> queryStringMap;

    private final HttpProtocol httpProtocol;

    private RequestLine(
            final HttpMethod method,
            final String path,
            final Map<String, String> queryStringMap,
            final HttpProtocol httpProtocol
    ) {
        this.method = method;
        this.path = path;
        this.queryStringMap = queryStringMap;
        this.httpProtocol = httpProtocol;
    }

    public static RequestLine from(final String requestLine) {
        String[] requestLineElements = requestLine.split(REQUEST_LINE_SPLITERATOR);

        String method = requestLineElements[0];

        String[] pathAndQueryStrings = requestLineElements[1].split("\\?");
        String path = pathAndQueryStrings[0];
        Map<String, String> queryStringMap = createQueryStringMap(pathAndQueryStrings);

        HttpProtocol httpProtocol = HttpProtocol.from(requestLineElements[2]);

        return new RequestLine(HttpMethod.from(method), path, queryStringMap, httpProtocol);
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


    public Map<String, String> getQueryStringMap() {
        return queryStringMap;
    }

    public String getVersion() {
        return httpProtocol.getVersion();
    }
}
