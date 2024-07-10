package org.apache.coyote.handler;

import camp.nextstep.db.InMemoryUserRepository;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.file.FileReader;
import org.apache.http.HttpPath;
import org.apache.http.body.HttpFileBody;
import org.apache.http.session.HttpCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);
    private static final String MAPPING_PATH = "/login";
    private static final String LOGIN_PAGE = "/login.html";
    private static final String SUCCESS_PAGE = "/index.html";
    private static final String FAIL_PAGE = "/401.html";

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (!request.matchPath(MAPPING_PATH)) {
            throw new NotSupportHandlerException();
        }

        var loginRequest = toLoginRequest(request);
        if (loginRequest == null) {
            return toLoginPage();
        }

        return toLoginResponse(loginRequest);
    }

    private LoginRequest toLoginRequest(final HttpRequest request) {
        var account = request.getBodyValue("account");
        var password = request.getBodyValue("password");
        if (account == null || password == null || request.isGet()) {
            return null;
        }
        return new LoginRequest(account, password);
    }

    private HttpResponse toLoginPage() {
        try {
            final var file = FileReader.read(LOGIN_PAGE);
            return new HttpResponse(new HttpFileBody(file));
        } catch (IOException e) {
            throw new NotSupportHandlerException();
        }
    }

    private HttpResponse toLoginResponse(final LoginRequest request) {
        if (login(request)) {
            return new HttpResponse(new HttpPath(SUCCESS_PAGE)).addCookie(HttpCookie.ofJSessionId("cookie"));
        }
        return new HttpResponse(new HttpPath(FAIL_PAGE));
    }

    private boolean login(final LoginRequest request) {
        var user = InMemoryUserRepository.findByAccount(request.account()).orElse(null);
        if (user != null && user.checkPassword(request.password())) {
            log.info(user.toString());
            return true;
        }
        return false;
    }
}


record LoginRequest(String account, String password) {
}
