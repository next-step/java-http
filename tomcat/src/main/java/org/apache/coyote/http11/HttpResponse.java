package org.apache.coyote.http11;

import org.apache.session.Session;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpResponse {
    private static final String CRLF = "\r\n";
    private static final String EMPTY = "";

    private final HttpRequest httpRequest;
    private final ResourceFinder resourceFinder;

    private final StatusLine statusLine;
    private final HttpHeaders httpHeaders;
    private String responseBody;

    private HttpResponse(
            final HttpRequest httpRequest,
            final ResourceFinder resourceFinder,
            final StatusLine statusLine,
            final HttpHeaders httpHeaders,
            final String responseBody
    ) {
        this.httpRequest = httpRequest;
        this.resourceFinder = resourceFinder;
        this.statusLine = statusLine;
        this.httpHeaders = httpHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse of(final HttpRequest httpRequest, final ResourceFinder resourceFinder) {
        return new HttpResponse(
                httpRequest,
                resourceFinder,
                StatusLine.from(httpRequest.getProtocol()),
                HttpHeaders.create(),
                EMPTY
        );
    }

    public String createMessage() {
        handleSetCookieHeader();
        return String.join(CRLF,
                "%s ".formatted(statusLine.createResponseMessage()),
                "%s".formatted(httpHeaders.createMessage()),
                "",
                responseBody);
    }

    private void handleSetCookieHeader() {
        Session session = httpRequest.getSession();
        if (session != null && session.isNew()) {
            session.setIsNew(false);
            Cookie cookie = Cookie.createJSessionCookie(session.getId());
            httpHeaders.addHeader(HttpHeader.of(HttpHeaderName.SET_COOKIE.getValue(), cookie.createMessage()));
        }
    }

    public void addHeader(final HttpHeader httpHeader) {
        httpHeaders.addHeader(httpHeader);
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        statusLine.setHttpStatus(httpStatus);
    }

    public void sendRedirect(final String path) {
        addHeader(HttpHeader.of(HttpHeaderName.LOCATION.getValue(), path));
        setHttpStatus(HttpStatus.FOUND);
    }

    public void sendResource(final String resourcePath) {
        try {
            File resource = resourceFinder.findByPath(resourcePath);
            responseBody = new String(Files.readAllBytes(resource.toPath()));

            HttpHeader contentTypeHeader = HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getValue(), MediaType.from(resource).getValue() + ";charset=utf-8");
            addHeader(contentTypeHeader);

            HttpHeader contentLengthHeader = HttpHeader.of(HttpHeaderName.CONTENT_LENGTH.getValue(), String.valueOf(responseBody.getBytes().length));
            addHeader(contentLengthHeader);
        } catch (IOException e) {
            throw new ResourceNotFoundException();
        }
    }
}
