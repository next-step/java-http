package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.HttpMethod;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final HttpPath httpPath;
    private final String protocol;
    private final String version;

    public RequestLine(final HttpMethod httpMethod, final HttpPath httpPath, final String protocol, final String version) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.protocol = protocol;
        this.version = version;
    }

    public HttpMethod httpMethod() {
        return httpMethod;
    }

    public String url() {
        return httpPath.path();
    }

    public String protocol() {
        return protocol;
    }

    public String version() {
        return version;
    }

    public QueryParams queryParams() {
        return httpPath.queryParams();
    }

    public boolean isRootPath() {
        return httpPath.isRootPath();
    }

    public String contentTypeText() {
        return httpPath.contentTypeText();
    }
}