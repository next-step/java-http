package camp.nextstep.http.exception;

public class InvalidHttpRequestSpecException extends RuntimeException {
    public InvalidHttpRequestSpecException(Exception e) {
        super(e);
    }
}
