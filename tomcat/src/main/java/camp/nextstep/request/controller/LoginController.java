package camp.nextstep.request.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;
import org.apache.coyote.http11.model.QueryParams;
import org.apache.coyote.http11.session.HttpSession;
import org.apache.coyote.http11.session.SessionManager;

import java.util.NoSuchElementException;
import java.util.UUID;

public class LoginController extends AbstractController {

    private static final String LOGIN_URI = "/login.html";
    private static final String LOGIN_ACCOUNT_KEY = "account";
    private static final String LOGIN_PASSWORD_KEY = "password";
    private static final String SUCCESS_REDIRECT_PATH = "/index.html";
    private static final String FAILED_REDIRECT_PATH = "/401.html";

    private LoginController() {
    }

    public static LoginController getInstance() {
        return SingletonHelper.instance;
    }

    @Override
    public String url() {
        return LOGIN_URI;
    }

    @Override
    protected HttpResponse doGet(HttpRequest request) throws Exception {
        if (!request.httpRequestHeader().hasJSessionIdCookie()) {
            final String body = buildBodyFromReadFile(request.httpRequestHeader().requestLine().url());
            return buildOkHttpResponse(request, body);
        }
        final String key = request.httpRequestHeader().JSessionId();
        final HttpSession session = (HttpSession) SessionManager.getInstance()
                .findSession(key);

        if (session != null) {
            return buildRedirectHttpResponse(request, SUCCESS_REDIRECT_PATH);
        }

        final String body = buildBodyFromReadFile(request.httpRequestHeader().requestLine().url());
        return buildOkHttpResponse(request, body);
    }

    @Override
    protected HttpResponse doPost(HttpRequest request) {
        final QueryParams requestBody = request.requestBody();

        final User user = InMemoryUserRepository.findByAccount(requestBody.valueBy(LOGIN_ACCOUNT_KEY))
                .orElseThrow(NoSuchElementException::new);

        if (user.checkPassword(requestBody.valueBy(LOGIN_PASSWORD_KEY))) {
            return loginWithCookie(request);
        }

        return buildRedirectHttpResponse(request, FAILED_REDIRECT_PATH);
    }

    private HttpResponse loginWithCookie(final HttpRequest request) {
        if (request.httpRequestHeader().hasJSessionIdCookie()) {
            return buildRedirectHttpResponse(request, SUCCESS_REDIRECT_PATH);
        }

        final String uuid = UUID.randomUUID()
                .toString();
        request.addJSessionIdCookie(uuid);

        SessionManager.getInstance()
                .add(new HttpSession(uuid));

        return buildRedirectSetCookieHttpResponse(request, SUCCESS_REDIRECT_PATH, uuid);
    }

    private static class SingletonHelper {
        private static final LoginController instance = new LoginController();
    }
}
