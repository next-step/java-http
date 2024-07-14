package camp.nextstep.service;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UnauthorizedException;
import camp.nextstep.exception.UserNotFoundException;
import camp.nextstep.model.User;
import org.apache.coyote.http11.meta.HttpCookie;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestBody;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;

public class LoginService {

    private static final String ACCOUNT_PARAMETER = "account";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String USER_KEY = "user";

    public boolean isLoginUser(Request request) {
        Session session = SessionManager.getSession(request.getCookies());
        return session.hasAttribute(USER_KEY);
    }

    public void authenticateUser(Request request) {

        RequestBody requestBody = request.getRequestBody();
        String account = requestBody.getParameter(ACCOUNT_PARAMETER).toString();
        String password = requestBody.getParameter(PASSWORD_PARAMETER).toString();

        User user = findUserByAccount(account);

        if (!isPasswordValid(user, password)) {
            throw new UnauthorizedException();
        }

        HttpCookie httpCookie = request.getCookies();
        Session session = SessionManager.getSession(httpCookie);
        session.setAttribute(USER_KEY, user);
    }

    private User findUserByAccount(String account) {
        return InMemoryUserRepository.findByAccount(account)
            .orElseThrow(UserNotFoundException::new);
    }

    private boolean isPasswordValid(User user, String password) {
        return user.checkPassword(password);
    }
}
