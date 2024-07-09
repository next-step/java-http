package camp.nextstep.exception;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(String path) {
        super("Not Found: " + path);
    }
}
