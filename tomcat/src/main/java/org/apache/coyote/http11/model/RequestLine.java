package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.HttpMethod;

public class RequestLine implements HttpHeaderLine {
    private static final String BLANK = " ";
    private static final String QUESTION_MARK = "?";
    private static final String SLASH = "/";

    private final HttpMethod httpMethod;
    private final HttpPath httpPath;
    private final HttpProtocol protocol;
    private final HttpVersion version;

    public RequestLine(final HttpMethod httpMethod, final HttpPath httpPath, final String protocol, final String version) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.protocol = new HttpProtocol(protocol);
        this.version = new HttpVersion(version);
    }

    public HttpMethod httpMethod() {
        return httpMethod;
    }

    public String url() {
        return httpPath.path();
    }

    public String protocol() {
        return protocol.protocol();
    }

    public String version() {
        return version.version();
    }

    public QueryParams queryParams() {
        return httpPath.queryParams();
    }

    public String contentTypeText() {
        return httpPath.contentTypeText();
    }

    @Override
    public String toLine() {
        if (queryParams().isEmpty()) {
            return httpMethod.name() + BLANK + httpPath.path() + BLANK + protocol.protocol() + SLASH + version.version();
        }

        return httpMethod.name() + BLANK + httpPath.path() + QUESTION_MARK + queryParams().toLine() + BLANK + protocol.protocol() + SLASH + version.version();
    }
}
