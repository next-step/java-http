package camp.nextstep.domain.http;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    ;

    private final int statusCode;
    private final String reasonPhrase;

    HttpStatus(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }
}
