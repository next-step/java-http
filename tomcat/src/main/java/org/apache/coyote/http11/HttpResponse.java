package org.apache.coyote.http11;

import java.util.List;

public class HttpResponse {
    private static final String CRLF = "\r\n";

    private final StatusLine statusLine;
    private final HttpHeaders httpHeaders;
    private final String responseBody;

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

    public String createFormat() {
        return String.join(CRLF,
                "%s ".formatted(statusLine.createResponseMessage()),
                "%s".formatted(httpHeaders.createMessage()),
                "",
                responseBody);
    }
}
