package org.apache.coyote.http11;

public class StatusLine {
    private static final String RESPONSE_MESSAGE_FORMAT = "%s %s";

    private final HttpProtocol httpProtocol;
    private final HttpStatus httpStatus;

    private StatusLine(final HttpProtocol httpProtocol, final HttpStatus httpStatus) {
        this.httpProtocol = httpProtocol;
        this.httpStatus = httpStatus;
    }

    public static StatusLine of(final HttpProtocol httpProtocol, final HttpStatus status) {
        return new StatusLine(httpProtocol, status);
    }

    public String createResponseMessage() {
        return RESPONSE_MESSAGE_FORMAT.formatted(httpProtocol.getVersion(), httpStatus.createResponseMessage());
    }
}
