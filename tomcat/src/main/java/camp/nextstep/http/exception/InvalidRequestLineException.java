package camp.nextstep.http.exception;

public class InvalidRequestLineException extends RuntimeException {
    public InvalidRequestLineException(final String message) {
        super(message);
    }
}
