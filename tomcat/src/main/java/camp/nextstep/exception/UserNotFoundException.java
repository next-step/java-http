package camp.nextstep.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("account가 %s인 사용자를 찾을 수 없습니다.".formatted(id));
    }
}
