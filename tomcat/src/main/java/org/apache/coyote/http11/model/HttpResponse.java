package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.HttpStatusCode;

public class HttpResponse {
    private static final String BLANK_LINE = "\r\n";
    private static final String NEW_LINE = " " + BLANK_LINE;
    private final HttpStatusCode httpStatusCode;
    private final HttpHeaders httpHeaders;
    private final String body;

    public HttpResponse(final HttpStatusCode httpStatusCode, final HttpHeaders httpHeaders, final String body) {
        this.httpStatusCode = httpStatusCode;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public HttpResponse(final HttpStatusCode httpStatusCode, final HttpHeaders httpHeaders) {
        this(httpStatusCode, httpHeaders, "");
    }

    private String buildStatusLine() {
        return httpStatusCode.responseMessage() + NEW_LINE;
    }

    private String buildLocationHeader(final String location) {
        return "Location: " + location + NEW_LINE;
    }

    private String buildSetCookieHeader(final String cookie) {
        return "Set-Cookie: " + cookie + NEW_LINE;
    }

    private String buildContentTypeHeader() {
        return "Content-Type: " + httpHeaders.requestLine().contentTypeText() + ";charset=utf-8" + NEW_LINE;
    }

    private String buildContentLengthHeader() {
        return "Content-Length: " + body.getBytes().length + NEW_LINE;
    }

    private String buildCookieHeader() {
        if (httpHeaders.hasCookie()) {
            return httpHeaders.httpCookie().toLine() + NEW_LINE;
        }

        return "";
    }

    public String buildRedirectResponse(final String location) {
        return buildStatusLine() +
                buildLocationHeader(location) +
                BLANK_LINE +
                body;
    }

    public String buildRedirectSetCookieResponse(final String location, final String cookie) {
        return buildStatusLine() +
                buildLocationHeader(location) +
                buildSetCookieHeader(cookie) +
                BLANK_LINE +
                body;
    }

    public String buildOkResponse() {
        return buildStatusLine() +
                buildContentTypeHeader() +
                buildContentLengthHeader() +
                buildCookieHeader() +
                BLANK_LINE +
                body;
    }
}
