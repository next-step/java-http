package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.domain.http.HttpCookie;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.response.HttpResponse;
import camp.nextstep.domain.session.Session;
import camp.nextstep.domain.session.SessionManager;
import camp.nextstep.model.User;

public class RegisterController extends AbstractController {

    private static final String INDEX_PAGE_PATH = "/index.html";

    private static final String REGISTER_ACCOUNT_KEY = "account";
    private static final String REGISTER_PASSWORD_KEY = "password";
    private static final String REGISTER_EMAIL_KEY = "email";

    private static final String SESSION_USER_KEY = "user";

    @Override
    protected HttpResponse doPost(HttpRequest httpRequest) {
        User user = new User(
                httpRequest.getHttpRequestBodyValue(REGISTER_ACCOUNT_KEY),
                httpRequest.getHttpRequestBodyValue(REGISTER_PASSWORD_KEY),
                httpRequest.getHttpRequestBodyValue(REGISTER_EMAIL_KEY)
        );
        InMemoryUserRepository.save(user);
        Session session = Session.createNewSession();
        session.setAttribute(SESSION_USER_KEY, user);
        SessionManager.add(session);
        return HttpResponse.found(httpRequest.getHttpProtocol(), INDEX_PAGE_PATH)
                .addCookie(HttpCookie.sessionCookie(session));
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) {
        return handlePath(httpRequest);
    }
}
