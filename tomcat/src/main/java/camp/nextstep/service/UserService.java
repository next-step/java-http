package camp.nextstep.service;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;

import java.util.Map;

public class UserService {

    public User getUser(Map<String, Object> queryParamMap) {
        String account = (String) queryParamMap.get("account");
        return InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다"));
    }

}
