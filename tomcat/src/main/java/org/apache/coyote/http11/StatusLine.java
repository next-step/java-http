package org.apache.coyote.http11;

public class StatusLine {
    private static final String RESPONSE_MESSAGE_FORMAT = "%s %s";

    private final HttpProtocol httpProtocol;
    private HttpStatus httpStatus;

    private StatusLine(final HttpProtocol httpProtocol, final HttpStatus httpStatus) {
        this.httpProtocol = httpProtocol;
        this.httpStatus = httpStatus;
    }

    public static StatusLine of(final HttpProtocol httpProtocol, final HttpStatus status) {
        return new StatusLine(httpProtocol, status);
    }

    public static StatusLine from(final HttpProtocol protocol) {
        return new StatusLine(protocol, HttpStatus.OK);
    }

    public String createResponseMessage() {
        return RESPONSE_MESSAGE_FORMAT.formatted(httpProtocol.getVersion(), httpStatus.createResponseMessage());
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
