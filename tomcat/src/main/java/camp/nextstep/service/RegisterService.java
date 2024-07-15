package camp.nextstep.service;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.meta.HttpCookie;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestBody;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;

public class RegisterService {

    private static final String ACCOUNT_PARAMETER = "account";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String USER_KEY = "user";

    public void registerUser(Request request) {
        RequestBody requestBody = request.getRequestBody();
        String account = requestBody.getParameter(ACCOUNT_PARAMETER).toString();
        String password = requestBody.getParameter(PASSWORD_PARAMETER).toString();
        String email = requestBody.getParameter(EMAIL_PARAMETER).toString();

        User user = new User(account, password, email);
        InMemoryUserRepository.save(user);

        HttpCookie httpCookie = request.getCookies();
        Session session = SessionManager.getSession(httpCookie);
        session.setAttribute(USER_KEY, user);
    }
}
