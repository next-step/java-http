package org.apache.coyote.http11.request.model;

import org.apache.coyote.http11.HttpMethod;

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

    public static RequestLine createRequestLineWithRedirectPath(RequestLine requestLine, String redirectPath) {
        return new RequestLine(requestLine.getHttpMethod(), Path.createPathWithRedirectPath(requestLine.getPath(), redirectPath), requestLine.getProtocol(), requestLine.getVersion());
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

    public boolean hasRequestBody() {
        return HttpMethod.hasPostOrPutOrPatchMethod(httpMethod);
    }

    public String getExtension() {
        return path.getExtension();
    }
}
