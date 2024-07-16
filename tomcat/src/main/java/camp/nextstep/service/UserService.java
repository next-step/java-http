package camp.nextstep.service;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;

import java.util.Map;

public class UserService {

    public User getUser(Map<String, Object> queryParamMap) {
        String account = (String) queryParamMap.get("account");
        return InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new UnauthroizedUserException("회원 정보를 찾을 수 없습니다"));
    }

    public User register(Map<String, Object> queryParamMap) {

        String account = (String) queryParamMap.get("account");
        String password = (String) queryParamMap.get("password");
        String email = (String) queryParamMap.get("email");

        return InMemoryUserRepository.save(new User(account, password, email));
    }

    public void login(Map<String, Object> queryParamMap) {
        String account = (String) queryParamMap.get("account");
        String password = (String) queryParamMap.get("password");

        InMemoryUserRepository.findByAccount(account)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(() -> new UnauthroizedUserException("회원 정보를 찾을 수 없습니다"));
    }
}