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

    public HttpStatusCode code() {
        return httpStatusCode;
    }

    public HttpHeaders httpHeaders() {
        return httpHeaders;
    }

    public String buildResponse() {
        ResponseBuilder builder = new ResponseBuilder();
        if (httpStatusCode == HttpStatusCode.OK) {
            return builder.buildOkResponse();
        }

        if (httpStatusCode == HttpStatusCode.FOUND) {
            return builder.buildFoundResponse();
        }

        return builder.buildNotFoundResponse();
    }

    public void addLocationHeader(final String location) {
        httpHeaders.addLocation(location);
    }

    public void addJSessionId(String cookie) {
        httpHeaders.addJSessionId(cookie);
    }

    private class ResponseBuilder {
        private static final String LOCATION_HEADER = "Location: ";
        private static final String SET_COOKIE_HEADER = "Set-Cookie: ";
        private static final String CONTENT_TYPE_HEADER = "Content-Type: ";
        private static final String CONTENT_LENGTH_HEADER = "Content-Length: ";
        private static final String CHARSET = ";charset=utf-8";

        public String buildOkResponse() {
            return buildStatusLine() +
                    buildContentTypeHeader() +
                    buildContentLengthHeader() +
                    buildCookieHeader() +
                    BLANK_LINE +
                    body;
        }

        public String buildFoundResponse() {
            if (httpHeaders.hasJSessionIdCookie()) {
                return buildRedirectSetCookieResponse(httpHeaders.location(), httpHeaders.httpCookie().toLine());
            }

            return buildRedirectResponse(httpHeaders.location());
        }

        public String buildNotFoundResponse() {
            return HttpStatusCode.NOT_FOUND.responseMessage() + NEW_LINE + BLANK_LINE;
        }

        private String buildStatusLine() {
            return httpStatusCode.responseMessage() + NEW_LINE;
        }

        private String buildLocationHeader(final String location) {
            return LOCATION_HEADER + location + NEW_LINE;
        }

        private String buildSetCookieHeader(final String cookie) {
            return SET_COOKIE_HEADER + cookie + NEW_LINE;
        }

        private String buildContentTypeHeader() {
            return CONTENT_TYPE_HEADER + httpHeaders.requestLine().contentTypeText() + CHARSET + NEW_LINE;
        }

        private String buildContentLengthHeader() {
            return CONTENT_LENGTH_HEADER + body.getBytes().length + NEW_LINE;
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
    }
}
