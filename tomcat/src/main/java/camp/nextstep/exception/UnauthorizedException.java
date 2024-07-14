package camp.nextstep.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("올바르지 않은 패스워드입니다.");
    }
}
