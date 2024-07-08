package org.apache.coyote.http11.requestline;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final Path path;
    private final String protocol;
    private final String version;

    public RequestLine(HttpMethod httpMethod, Path path, String protocol, String version) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine from(RequestParser requestParser) {
        HttpMethod httpMethod = requestParser.getHttpMethod();
        Path path = requestParser.getPath();
        String protocol = requestParser.getProtocol();
        String version = requestParser.getVersion();
        return new RequestLine(httpMethod, path, protocol, version);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Path getPath() {
        return path;
    }

    public String getRequestPath() {
        return path.requestPath();
    }

    public QueryStrings getQueryStrings() {
        return path.queryStrings();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
