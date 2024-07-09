package org.apache.coyote.http11.request.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpSession;
import org.apache.coyote.http11.model.QueryParams;
import org.apache.coyote.http11.session.SessionManager;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

public class LoginHandler extends AbstractRequestHandler {

    private static final String LOGIN_ACCOUNT_KEY = "account";
    private static final String LOGIN_PASSWORD_KEY = "password";
    private static final String SUCCESS_REDIRECT_PATH = "/index.html";
    private static final String FAILED_REDIRECT_PATH = "/401.html";
    private static final String JSESSIONID_KEY = "JSESSIONID";
    private static final String EQUAL = "=";

    @Override
    public String handle(final HttpRequest request) throws IOException {
        if (!request.hasRequestBody()) {
            return checkSessionLogin(request);
        }

        return login(request);
    }

    private String checkSessionLogin(final HttpRequest request) throws IOException {
        if (!request.httpRequestHeader().hasJSessionIdCookie()) {
            final String body = buildBodyFromReadFile(request.httpRequestHeader().requestLine().url());
            return buildOkHttpResponse(request.httpRequestHeader(), body);
        }

        final HttpSession session = (HttpSession) SessionManager.getInstance()
                .findSession(request.httpRequestHeader().JSessionId());

        if (session != null) {
            return buildRedirectHttpResponse(request.httpRequestHeader(), SUCCESS_REDIRECT_PATH);
        }

        final String body = buildBodyFromReadFile(request.httpRequestHeader().requestLine().url());
        return buildOkHttpResponse(request.httpRequestHeader(), body);
    }


    private String login(final HttpRequest request) {
        final QueryParams requestBody = request.requestBody();

        final User user = InMemoryUserRepository.findByAccount(requestBody.valueBy(LOGIN_ACCOUNT_KEY))
                .orElseThrow(NoSuchElementException::new);

        if (user.checkPassword(requestBody.valueBy(LOGIN_PASSWORD_KEY))) {
            return loginWithCookie(request);
        }

        return buildRedirectHttpResponse(request.httpRequestHeader(), FAILED_REDIRECT_PATH);
    }

    private String loginWithCookie(final HttpRequest request) {
        if (request.httpRequestHeader().hasJSessionIdCookie()) {
            return buildRedirectHttpResponse(request.httpRequestHeader(), SUCCESS_REDIRECT_PATH);
        }

        final String uuid = UUID.randomUUID()
                .toString();
        request.addJSessionIdCookie(uuid);

        SessionManager.getInstance()
                .add(new HttpSession(uuid));

        return buildRedirectSetCookieHttpResponse(request.httpRequestHeader(), SUCCESS_REDIRECT_PATH, JSESSIONID_KEY + EQUAL + uuid);
    }
}
