package org.apache.coyote.http11;

public enum HttpStatusCode {
    SUCCESS(200),
    REDIRECT(302),
    UNAUTHORIZED(401),
    ;

    private final int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
