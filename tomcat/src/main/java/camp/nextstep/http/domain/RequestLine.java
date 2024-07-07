package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidRequestLineException;

public class RequestLine {

    private static final int METHOD_INDEX = 0;
    public static final int PATH_INDEX = 1;
    private final HttpMethod method;
    private final HttpPath path;
    private final String protocol;
    private final String version;

    public RequestLine(final String requestLine) {
        final String[] split = parseRequestLine(requestLine);
        this.method = HttpMethod.valueOf(split[METHOD_INDEX]);
        this.path = new HttpPath(split[PATH_INDEX]);
        this.protocol = split[2].split("/")[0];
        this.version = split[2].split("/")[1];
    }

    private String[] parseRequestLine(final String requestLine) {
        final String[] split = requestLine.split(" ");
        if (split.length != 3) {
            throw new InvalidRequestLineException("Invalid request line: " + requestLine);
        }
        return split;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpPath getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
