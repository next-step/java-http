package org.apache.coyote.http11;

public class HttpResponse {
    private final StatusLine statusLine;
    private final MediaType mediaType;
    private final String responseBody;

    private HttpResponse(final StatusLine statusLine, final MediaType mediaType, final String responseBody) {
        this.statusLine = statusLine;
        this.mediaType = mediaType;
        this.responseBody = responseBody;
    }

    public static HttpResponse from(
            final HttpProtocol httpProtocol,
            final HttpStatus httpStatus,
            final MediaType mediaType,
            final String responseBody
    ) {
        return new HttpResponse(StatusLine.of(httpProtocol, httpStatus), mediaType, responseBody);
    }

    public String createFormat() {
        return String.join("\r\n",
                "%s ".formatted(statusLine.createResponseMessage()),
                "Content-Type: %s;charset=utf-8 ".formatted(mediaType.getValue()),
                "Content-Length: " + responseBody.getBytes().length + " ",
                "",
                responseBody);
    }
}
