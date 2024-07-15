package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UserNotFoundException;
import camp.nextstep.model.User;
import org.apache.coyote.http11.AbstractController;
import org.apache.coyote.http11.HttpRequest;
import org.apache.coyote.http11.HttpResponse;
import org.apache.session.Session;

import java.util.Objects;

public class LoginController extends AbstractController {
    private static final String INDEX_PATH = "/index.html";
    private static final String UNAUTHORIZED_PATH = "/401.html";

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) {
        String account = request.getBodyValue("account");
        User user = InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new UserNotFoundException(account));
        String password = request.getBodyValue("password");

        if (user.checkPassword(password)) {
            Session session = request.getSession(true);
            session.addAttribute("user", user);
            response.sendRedirect(INDEX_PATH);
            return;
        }

        response.sendRedirect(UNAUTHORIZED_PATH);
    }

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) {
        if (Objects.nonNull(request.getSession())) {
            response.sendRedirect(INDEX_PATH);
            return;
        }

        response.sendResource("/login.html");
    }
}
