package camp.nextstep.service;

import camp.nextstep.UserDto;
import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;

import java.util.Map;

public class UserService {


    public User register(UserDto userDto) {

        var account = userDto.account();
        var password = userDto.password();
        var email = userDto.email();

        return InMemoryUserRepository.save(new User(account, password, email));
    }

    public User login(UserDto userDto) {
        var account = userDto.account();
        var password = userDto.password();

        return InMemoryUserRepository.findByAccount(account)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(() -> new UnauthroizedUserException("회원 정보를 찾을 수 없습니다"));
    }
}