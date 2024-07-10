package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.http.domain.*;
import camp.nextstep.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private static final String SESSION_USER_KEY = "user";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String INDEX_PATH = "/index.html";
    private static final String LOGIN_PATH = "/login.html";
    private static final String UNAUTHORIZED_PATH = "/401.html";

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        final HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SESSION_USER_KEY) != null) {
            response.sendRedirect(INDEX_PATH);
            return;
        }

        response.setContentType(ContentType.HTML);
        response.forward(LOGIN_PATH);
    }

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        final RequestBody requestBody = request.getRequestBody();
        final Optional<User> userOptional = InMemoryUserRepository.findByAccount(requestBody.get(ACCOUNT));

        if (userOptional.isEmpty()) {
            response.sendRedirect(UNAUTHORIZED_PATH);
            return;
        }

        final User user = userOptional.get();
        if (user.checkPassword(requestBody.get(PASSWORD))) {
            processLogin(request, response, user);
            return;
        }
        response.sendRedirect(UNAUTHORIZED_PATH);
    }

    private void processLogin(final HttpRequest request, final HttpResponse response, final User user) throws IOException {
        log.info("user : {}", user);
        final HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(SESSION_USER_KEY, user);
        response.setSession(httpSession.getId());
        response.sendRedirect(INDEX_PATH);
    }
}
