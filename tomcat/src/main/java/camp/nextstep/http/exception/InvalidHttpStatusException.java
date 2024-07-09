package camp.nextstep.http.exception;

public class InvalidHttpStatusException extends RuntimeException {
    public InvalidHttpStatusException(final String message) {
        super(message);
    }
}
