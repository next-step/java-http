package org.apache.coyote.http;

public class ResponseLine {
    public static final String RESPONSE_LINE_SEPARATOR = " ";
    private HttpVersion httpVersion;
    private StatusCode statusCode;

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(final HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String toResponseLine() {
        return this.httpVersion.getVersion() + RESPONSE_LINE_SEPARATOR + this.statusCode.getCode() + RESPONSE_LINE_SEPARATOR + this.statusCode.getReasonPhrase();
    }
}
