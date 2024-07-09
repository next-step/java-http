package org.apache.coyote.http11.model;

public class HttpRequest {
    private final HttpRequestHeader requestHeader;
    private final QueryParams requestBody;

    public HttpRequest(final HttpRequestHeader requestHeader, final QueryParams requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public HttpRequestHeader httpRequestHeader() {
        return this.requestHeader;
    }

    public QueryParams requestBody() {
        return this.requestBody;
    }

    public boolean hasRequestBody() {
        return !requestHeader.requestLine()
                .httpMethod()
                .isGetMethod()
                && !requestBody.isEmpty();
    }
}
