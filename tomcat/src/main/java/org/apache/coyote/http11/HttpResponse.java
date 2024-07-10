package org.apache.coyote.http11;

public class HttpResponse {
    private final HttpProtocol httpProtocol;
    private final int status;
    private final MediaType mediaType;
    private final String responseBody;

    private HttpResponse(final HttpProtocol httpProtocol, final int status, final MediaType mediaType, final String responseBody) {
        this.httpProtocol = httpProtocol;
        this.status = status;
        this.mediaType = mediaType;
        this.responseBody = responseBody;
    }

    public static HttpResponse from(final HttpProtocol httpProtocol, final MediaType mediaType, final String responseBody) {
        return new HttpResponse(httpProtocol, 200, mediaType, responseBody);
    }

    public String createFormat() {
        return String.join("\r\n",
                "%s %d OK ".formatted(httpProtocol.getVersion(), status),  //TODO statusLine
                "Content-Type: %s;charset=utf-8 ".formatted(mediaType.getValue()),
                "Content-Length: " + responseBody.getBytes().length + " ",
                "",
                responseBody);
    }
}
