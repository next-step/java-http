package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.HttpStatusCode;

public class HttpResponse {
    private static final String NEW_LINE = "\r\n";

    private final HttpStatusCode httpStatusCode;
    private final HttpRequestHeader httpRequestHeader;
    private final String body;

    public HttpResponse(final HttpStatusCode httpStatusCode, final HttpRequestHeader httpRequestHeader, final String body) {
        this.httpStatusCode = httpStatusCode;
        this.httpRequestHeader = httpRequestHeader;
        this.body = body;
    }

    public HttpResponse(final HttpStatusCode httpStatusCode, final HttpRequestHeader httpRequestHeader) {
        this(httpStatusCode, httpRequestHeader, "");
    }

    public String buildRedirectResponse(final String location) {
        final StringBuilder response = new StringBuilder();

        response.append(httpStatusCode.responseMessage()).append(NEW_LINE)
                .append("Location: ").append(location).append(" ").append(NEW_LINE)
                .append(NEW_LINE)
                .append(body);

        return response.toString();
    }

    public String buildOkResponse() {
        final StringBuilder response = new StringBuilder();

        response.append(httpStatusCode.responseMessage()).append(NEW_LINE)
                .append("Content-Type: ").append(httpRequestHeader.requestLine().contentTypeText()).append(";charset=utf-8 ").append(NEW_LINE)
                .append("Content-Length: ").append(body.getBytes().length).append(" ").append(NEW_LINE);

        if (httpRequestHeader.hasCookie()) {
            final String cookie = httpRequestHeader.httpCookie().toLine();
            response.append(cookie).append(NEW_LINE);
        }

        response.append(NEW_LINE)
                .append(body);

        return response.toString();
    }
}
