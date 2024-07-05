package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.HttpMethod;

import java.util.HashMap;

public class RequestLine {
    private final HttpMethod httpMethod;
    private final String url;
    private final String protocol;
    private final String version;
    private HashMap<String, String> queryParams;

    public RequestLine(final HttpMethod httpMethod, final String url, final String protocol, final String version) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
        this.version = version;
    }

    public RequestLine(final HttpMethod httpMethod, final String url, final String protocol, final String version, final HashMap<String, String> queryParams) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.protocol = protocol;
        this.version = version;
        this.queryParams = queryParams;
    }

    public HttpMethod httpMethod() {
        return httpMethod;
    }

    public String url() {
        return url;
    }

    public String protocol() {
        return protocol;
    }

    public String version() {
        return version;
    }

    public HashMap<String, String> queryParams() {
        return queryParams;
    }
}
