package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.HttpMethod;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final Path path;
    private final String protocol;
    private final String version;

    public RequestLine(final HttpMethod httpMethod, final Path path, final String protocol, final String version) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public HttpMethod httpMethod() {
        return httpMethod;
    }

    public String url() {
        return path.path();
    }

    public String protocol() {
        return protocol;
    }

    public String version() {
        return version;
    }

    public QueryParams queryParams() {
        return path.queryParams();
    }
}
