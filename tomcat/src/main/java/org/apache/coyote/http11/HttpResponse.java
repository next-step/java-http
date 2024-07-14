package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HttpResponse {
    private static final String CRLF = "\r\n";
    private static final String EMPTY = "";

    private final ResourceFinder resourceFinder;

    private final StatusLine statusLine;
    private final HttpHeaders httpHeaders;
    private String responseBody;

    private HttpResponse(final ResourceFinder resourceFinder, final StatusLine statusLine, final HttpHeaders httpHeaders, final String responseBody) {
        this.resourceFinder = resourceFinder;
        this.statusLine = statusLine;
        this.httpHeaders = httpHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse of(final HttpProtocol protocol, final ResourceFinder resourceFinder) {
        HttpHeaders httpHeaders = HttpHeaders.create();
        return new HttpResponse(resourceFinder, StatusLine.from(protocol), httpHeaders, EMPTY);
    }

    public String createFormat() {
        return String.join(CRLF,
                "%s ".formatted(statusLine.createResponseMessage()),
                "%s".formatted(httpHeaders.createMessage()),
                "",
                responseBody);
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

    public void setCookie(final Cookie cookie) {
        addHeader(HttpHeader.of(HttpHeaderName.SET_COOKIE.getValue(), cookie.createMessage()));
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
