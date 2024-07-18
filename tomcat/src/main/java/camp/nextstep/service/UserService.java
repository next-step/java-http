package camp.nextstep.service;

import static camp.nextstep.db.InMemoryUserRepository.findByAccount;

public class UserService {
    public boolean isUserPresent(String account, String password) {
        return findByAccount(account)
                .map(v -> v.checkPassword(password))
                .orElse(false);
    }
}
