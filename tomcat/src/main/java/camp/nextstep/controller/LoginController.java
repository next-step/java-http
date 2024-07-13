package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.http.domain.ContentType;
import camp.nextstep.http.domain.HttpRequest;
import camp.nextstep.http.domain.HttpResponse;
import camp.nextstep.http.domain.HttpSession;
import camp.nextstep.http.domain.RequestBody;
import camp.nextstep.http.domain.Route;
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

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException {
        final HttpSession session = request.getSession();
        if (session.getAttribute(SESSION_USER_KEY) != null) {
            response.sendRedirect(Route.INDEX);
            return;
        }

        response.setContentType(ContentType.HTML);
        response.forward(Route.LOGIN);
    }

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException {
        final RequestBody requestBody = request.getRequestBody();
        final Optional<User> userOptional = InMemoryUserRepository.findByAccount(requestBody.get(ACCOUNT));

        if (userOptional.isEmpty() || !userOptional.get().checkPassword(requestBody.get(PASSWORD))) {
            response.sendRedirect(Route.UNAUTHORIZED);
            return;
        }

        processLogin(request, response, userOptional.get());
    }

    private void processLogin(final HttpRequest request, final HttpResponse response, final User user) throws IOException {
        log.info("user : {}", user);
        final HttpSession httpSession = request.getSession();
        httpSession.setAttribute(SESSION_USER_KEY, user);
        response.setSession(httpSession.getId());
        response.sendRedirect(Route.INDEX);
    }
}
