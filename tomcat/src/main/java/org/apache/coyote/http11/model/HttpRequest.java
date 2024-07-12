package org.apache.coyote.http11.model;

public class HttpRequest {
    private final HttpHeaders requestHeader;
    private final QueryParams requestBody;

    public HttpRequest(final HttpHeaders requestHeader, final QueryParams requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public HttpHeaders httpRequestHeader() {
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

    public void addJSessionIdCookie(final String uuid) {
        requestHeader.addJSessionId(uuid);
    }
}
