package org.apache.coyote.http11.request.model;

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

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Path getPath() {
        return path;
    }

    public String getUrlPath() {
        return path.urlPath();
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
