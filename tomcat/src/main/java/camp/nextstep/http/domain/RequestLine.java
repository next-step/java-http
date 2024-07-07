package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidRequestLineException;

public class RequestLine {

    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    public RequestLine(final String requestLine) {
        final String[] split = parseRequestLine(requestLine);
        this.method = split[0];
        this.path = split[1];
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

    public String getMethod() {
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
}
