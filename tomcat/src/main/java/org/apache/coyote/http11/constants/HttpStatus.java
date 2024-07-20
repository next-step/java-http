package org.apache.coyote.http11.constants;

public enum HttpStatus {
    SUCCESS(200, "OK"),
    FOUND(302, "Found"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;

    private final int code;
    private final String reasonPhrase;

    HttpStatus(int code, String message) {
        this.code = code;
        this.reasonPhrase = message;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhrase() { return reasonPhrase; }
}
