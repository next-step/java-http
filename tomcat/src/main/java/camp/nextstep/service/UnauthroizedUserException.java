package camp.nextstep.service;

public class UnauthroizedUserException extends RuntimeException {
    public UnauthroizedUserException(String msg) {
        super(msg);
    }
}
