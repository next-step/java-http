package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidRequestLineException;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestLine {
    private static final int REQUIRED_REQUEST_LINE_LENGTH = 3;
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int VERSION_INDEX = 2;
    private static final String DELIMITER = " ";

    private final HttpMethod method;
    private final HttpPath path;
    private final HttpVersion version;
    private final Map<String, String> queryString;

    public RequestLine(final String requestLine) {
        final String[] parsedRequestLine = parseRequestLine(requestLine);
        this.method = HttpMethod.valueOf(parsedRequestLine[METHOD_INDEX]);
        final String requestURI = parsedRequestLine[PATH_INDEX];
        this.path = new HttpPath(requestURI);
        this.queryString = parseQueryString(requestURI);
        this.version = new HttpVersion(parsedRequestLine[VERSION_INDEX]);
    }

    private Map<String, String> parseQueryString(final String requestURI) {
        final String[] splitRequestURI = requestURI.split("\\?");

        if (splitRequestURI.length < 2) {
            return Collections.emptyMap();
        }

        return Stream.of(splitRequestURI[1].split("&"))
                .map(s -> s.split("=", 2))
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
    }

    private String[] parseRequestLine(final String requestLine) {
        final String[] splitRequestLine = requestLine.split(DELIMITER);
        if (splitRequestLine.length != REQUIRED_REQUEST_LINE_LENGTH) {
            throw new InvalidRequestLineException("Invalid request line: " + requestLine);
        }
        return splitRequestLine;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpPath getPath() {
        return path;
    }

    public HttpVersion getVersion() {
        return version;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }
}
