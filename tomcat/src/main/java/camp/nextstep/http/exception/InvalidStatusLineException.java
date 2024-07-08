package camp.nextstep.http.exception;

public class InvalidStatusLineException extends RuntimeException {
    public InvalidStatusLineException(final String message) {
        super(message);
    }
}
