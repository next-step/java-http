package org.apache.coyote.http11.model.constant;

public enum HttpStatusCode {
    OK(200),
    CREATED(201),
    FOUND(302),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private static final String BLANK = " ";
    private static final String UNDERSCORE = "_";
    private static final String HTTP_STRING = "HTTP/1.1 ";

    private final int code;

    HttpStatusCode(final int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

    public String responseMessage() {
        return HTTP_STRING + code + BLANK + name().replace(UNDERSCORE, BLANK) + BLANK;
    }
}
