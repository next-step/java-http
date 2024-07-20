package camp.nextstep.service;

import camp.nextstep.http.exception.DuplicateUserException;
import camp.nextstep.model.User;

import static camp.nextstep.db.InMemoryUserRepository.findByAccount;
import static camp.nextstep.db.InMemoryUserRepository.save;

public class UserService {
    public boolean isUserPresent(String account, String password) {
        return findByAccount(account)
                .map(v -> v.checkPassword(password))
                .orElse(false);
    }

    public User findUser(String account, String password) {
        return findByAccount(account)
                .filter(v -> v.checkPassword(password))
                .orElse(null);
    }

    public void registerUser(String account, String password, String email) {
        findByAccount(account)
                .ifPresent(v -> {throw new DuplicateUserException("중복 유저");});

        User user = new User(account, password, email);
        save(user);
    }
}
