package org.apache.coyote.http11.response;

public class StatusLine {
    private final String httpVersion;
    private final StatusCode statusCode;

    public StatusLine(String httpVersion, StatusCode statusCode) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
    }

    public static StatusLine createOk() {
        return new StatusLine("HTTP/1.1", StatusCode.OK);
    }

    public static StatusLine createFound() {
        return new StatusLine("HTTP/1.1", StatusCode.FOUND);
    }

    public String convertToString() {
        return String.format("%s %d %s", httpVersion, statusCode.getCode(), statusCode.getReason());
    }
}
