package camp.nextstep.http.exception;

public class InvalidHttpHeaderException extends RuntimeException {
    public InvalidHttpHeaderException(final String message) {
        super(message);
    }
}
