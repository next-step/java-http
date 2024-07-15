package camp.nextstep.service;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http.Session;

import java.util.Objects;

public class UserService {

    public User findLoginUser(final Session session) {
        final Object user = session.getAttribute("user");

        if (user != null && !(user instanceof User)) {
            throw new RuntimeException("Invalid User");
        }

        return (User) user;
    }

    public boolean isInvalidLoginUser(final User foundAccount, final String password) {
        return foundAccount == null || !foundAccount.checkPassword(password);
    }

    public User findUserByAccount(final String account) {
        return InMemoryUserRepository.findByAccount(account).orElse(null);
    }

    public boolean isInvalidUserInfo(final String account, final String password, final String email) {
        return isInvalidInput(account) || isInvalidInput(password) || isInvalidInput(email);
    }

    private boolean isInvalidInput(final String input) {
        return Objects.isNull(input) || input.isBlank();
    }

    public void login(final User user, final Session session) {
        session.setAttribute("user", user);
    }

    public void register(final String account, final String password, final String email) {
        InMemoryUserRepository.save(new User(account, password, email));
    }
}
