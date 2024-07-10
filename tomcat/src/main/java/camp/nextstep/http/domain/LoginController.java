package camp.nextstep.http.domain;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LoginController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private static final String SESSION_USER_KEY = "user";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws Exception {
        final HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SESSION_USER_KEY) != null) {
            response.sendRedirect("/index.html");
            return;
        }

        response.setContentType(ContentType.HTML);
        response.forward("/login.html");
    }

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws Exception {
        final RequestBody requestBody = request.getRequestBody();
        final Optional<User> userOptional = InMemoryUserRepository.findByAccount(requestBody.get(ACCOUNT));

        if (userOptional.isEmpty()) {
            response.sendRedirect("/401.html");
            return;
        }

        final User user = userOptional.get();
        if (user.checkPassword(requestBody.get(PASSWORD))) {
            log.info("user : {}", user);
            final HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute(SESSION_USER_KEY, user);
            response.setSession(httpSession.getId());
            response.sendRedirect("/index.html");
            return;
        }
        response.sendRedirect("/401.html");
    }
}
