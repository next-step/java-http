package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.domain.http.HttpCookie;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.response.HttpResponse;
import camp.nextstep.domain.session.Session;
import camp.nextstep.domain.session.SessionManager;
import camp.nextstep.model.User;

public class LoginController extends AbstractController {

    private static final String INDEX_PAGE_PATH = "/index.html";
    private static final String UNAUTHORIZED_PAGE_PATH = "/401.html";

    private static final String LOGIN_ACCOUNT_KEY = "account";
    private static final String LOGIN_PASSWORD_KEY = "password";

    private static final String SESSION_USER_KEY = "user";

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        String account = httpRequest.getHttpRequestBodyValue(LOGIN_ACCOUNT_KEY);
        String password = httpRequest.getHttpRequestBodyValue(LOGIN_PASSWORD_KEY);
        User user = InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 account입니다."));
        if (user.checkPassword(password)) {
            return handleLoginUser(httpRequest, user);
        }
        return HttpResponse.found(httpRequest.getHttpProtocol(), UNAUTHORIZED_PAGE_PATH);
    }

    private HttpResponse handleLoginUser(HttpRequest httpRequest, User user) {
        Session session = Session.createNewSession();
        session.setAttribute(SESSION_USER_KEY, user);
        SessionManager.add(session);
        return HttpResponse.found(httpRequest.getHttpProtocol(), INDEX_PAGE_PATH)
                .addCookie(HttpCookie.sessionCookie(session));
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        if (httpRequest.containsSessionId() && isLoginSession(httpRequest)) {
            return HttpResponse.found(httpRequest.getHttpProtocol(), INDEX_PAGE_PATH);
        }
        return handleStaticPath(httpRequest);
    }

    private boolean isLoginSession(HttpRequest httpRequest) {
        String sessionId = httpRequest.getSessionId();
        return SessionManager.findSession(sessionId)
                .isPresent();
    }
}
