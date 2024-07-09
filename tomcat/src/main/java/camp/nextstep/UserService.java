package camp.nextstep;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.Http11Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService() {
    }

    public User findUserByAccountAndCheckPassword(String account, String password) {
        User user = InMemoryUserRepository.findByAccount(account).orElseThrow(
                () -> new RuntimeException("존재하지 않는 계정입니다."));
        if (!user.checkPassword(password)) {
            throw new RuntimeException("설정한 비밀번호와 다릅니다.");
        }
        log.info(user.toString());
        return user;
    }
}
