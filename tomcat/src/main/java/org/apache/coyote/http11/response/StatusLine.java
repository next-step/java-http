package org.apache.coyote.http11.response;

public class StatusLine {
    private final String httpVersion;
    private final StatusCode statusCode;

    public StatusLine(String httpVersion, StatusCode statusCode) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
    }

    public static StatusLine create(String httpVersion, StatusCode statusCode) {
        return new StatusLine(httpVersion, statusCode);
    }
}
