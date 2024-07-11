package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.HttpStatusCode;

public class HttpResponse {
    private static final String BLANK_LINE = "\r\n";
    private static final String NEW_LINE = " " + BLANK_LINE;
    private static final String SET_COOKIE_KEY = "Set-Cookie: ";
    private static final String LOCATION_KEY = "Location: ";
    private static final String CONTENT_TYPE_KEY = "Content-Type: ";
    private static final String CONTENT_LENGTH_KEY = "Content-Length: ";
    private static final String CHARSET = ";charset=utf-8";
    private static final String EMPTY = "";
    private final HttpStatusCode httpStatusCode;
    private final HttpHeaders httpHeaders;
    private final String body;

    public HttpResponse(final HttpStatusCode httpStatusCode, final HttpHeaders httpHeaders, final String body) {
        this.httpStatusCode = httpStatusCode;
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public HttpResponse(final HttpStatusCode httpStatusCode, final HttpHeaders httpHeaders) {
        this(httpStatusCode, httpHeaders, EMPTY);
    }

    private String buildStatusLine() {
        return httpStatusCode.responseMessage() + NEW_LINE;
    }

    private String buildLocationHeader(final String location) {
        return LOCATION_KEY + location + NEW_LINE;
    }

    private String buildSetCookieHeader(final String cookie) {
        return SET_COOKIE_KEY + cookie + NEW_LINE;
    }

    private String buildContentTypeHeader() {
        return CONTENT_TYPE_KEY + httpHeaders.requestLine().contentTypeText() + CHARSET + NEW_LINE;
    }

    private String buildContentLengthHeader() {
        return CONTENT_LENGTH_KEY + body.getBytes().length + NEW_LINE;
    }

    private String buildCookieHeader() {
        if (httpHeaders.hasCookie()) {
            return httpHeaders.httpCookie().toLine() + NEW_LINE;
        }

        return EMPTY;
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
