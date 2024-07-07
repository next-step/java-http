package camp.nextstep.http.exception;

public class InvalidHttpVersionException extends RuntimeException {
    public InvalidHttpVersionException(final String message) {
        super(message);
    }
}
