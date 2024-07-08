package camp.nextstep.http.exception;

public class InvalidStatusLineException extends RuntimeException {
    public InvalidStatusLineException(final String message) {
        super(message);
    }

    public InvalidStatusLineException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
