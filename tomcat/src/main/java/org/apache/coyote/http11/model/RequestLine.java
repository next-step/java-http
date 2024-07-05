package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.HttpMethod;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final Url url;
    private final String protocol;
    private final String version;

    public RequestLine(final HttpMethod httpMethod, final Url url, final String protocol, final String version) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
        this.version = version;
    }

    public HttpMethod httpMethod() {
        return httpMethod;
    }

    public String url() {
        return url.path();
    }

    public String protocol() {
        return protocol;
    }

    public String version() {
        return version;
    }

    public QueryParams queryParams() {
        return url.queryParams();
    }
}
