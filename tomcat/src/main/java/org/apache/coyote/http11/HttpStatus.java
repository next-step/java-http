package org.apache.coyote.http11;

public enum HttpStatus {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),;

    private static final String RESPONSE_MESSAGE_FORMAT = "%d %s";

    private final int code;
    private final String message;

    HttpStatus(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String createResponseMessage() {
        return RESPONSE_MESSAGE_FORMAT.formatted(code, message);
    }
}
