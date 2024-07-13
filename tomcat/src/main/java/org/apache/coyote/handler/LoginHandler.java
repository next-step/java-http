package org.apache.coyote.handler;

import camp.nextstep.db.InMemoryUserRepository;
import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.http.HttpPath;
import org.apache.http.session.HttpCookie;

public class LoginHandler implements Handler {
    private static final String SUCCESS_PAGE = "/index.html";
    private static final String FAIL_PAGE = "/401.html";

    @Override
    public HttpResponse handle(final HttpRequest request) {
        var session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return new HttpResponse(new HttpPath(SUCCESS_PAGE));
        }

        var loginRequest = new LoginRequest(request);
        return login(request, loginRequest);
    }

    private HttpResponse login(final HttpRequest httpRequest, final LoginRequest request) {
        var user = InMemoryUserRepository.findByAccount(request.account()).orElse(null);
        if (user == null || !user.checkPassword(request.password())) {
            return new HttpResponse(new HttpPath(FAIL_PAGE));
        }

        var session = httpRequest.getSession(true);
        session.setAttribute("user", user);
        return new HttpResponse(new HttpPath(SUCCESS_PAGE)).cookie(HttpCookie.ofSession(session));
    }
}
