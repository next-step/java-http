package camp.nextstep;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.catalina.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static ViewModel findUser(Map<String, Object> queryParamMap) {

        String account = (String) queryParamMap.get("account");
        User user = InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다"));
        log.info("user account: {}", user.getAccount());

        return new ViewModel("/login.html");
    }
}
