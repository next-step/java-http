package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class HttpResponse {
    private static final String CRLF = "\r\n";
    private static final String EMPTY = "";

    private final StatusLine statusLine;
    private final HttpHeaders httpHeaders;
    private String responseBody;

    private HttpResponse(final StatusLine statusLine, final HttpHeaders httpHeaders, final String responseBody) {
        this.statusLine = statusLine;
        this.httpHeaders = httpHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse of(final HttpProtocol httpProtocol, final HttpStatus httpStatus, final List<HttpHeader> httpHeaders, final String responseBody) {
        HttpHeaders httpHeaders1 = HttpHeaders.from(httpHeaders);
        httpHeaders1.addHeader(HttpHeader.of(HttpHeaderName.CONTENT_LENGTH.getValue(), String.valueOf(responseBody.getBytes().length)));
        return new HttpResponse(StatusLine.of(httpProtocol, httpStatus), httpHeaders1, responseBody);
    }

    public static HttpResponse from(final HttpProtocol protocol) {
        HttpHeaders httpHeaders = HttpHeaders.create();
        httpHeaders.addHeader(HttpHeader.of(HttpHeaderName.CONTENT_LENGTH.getValue(), "0"));
        return new HttpResponse(StatusLine.from(protocol), httpHeaders, EMPTY);
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
            File resource = new ResourceFinder().findByPath(resourcePath);
            responseBody = new String(Files.readAllBytes(resource.toPath()));

            MediaType mediaType = MediaType.from(resource);
            HttpHeader contentTypeHeader = HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getValue(), mediaType.getValue() + ";charset=utf-8");
            addHeader(contentTypeHeader);

            String contentLengthHeaderName = HttpHeaderName.CONTENT_LENGTH.getValue();
            String contentLength = String.valueOf(responseBody.getBytes().length);
            httpHeaders.replace(contentLengthHeaderName, contentLength);
        } catch (IOException e) {
            throw new ResourceNotFoundException();
        }
    }
}
