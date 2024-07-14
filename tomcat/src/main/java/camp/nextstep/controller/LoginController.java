package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UserNotFoundException;
import camp.nextstep.model.User;
import org.apache.session.Session;
import org.apache.coyote.http11.AbstractController;
import org.apache.coyote.http11.Cookie;
import org.apache.coyote.http11.HttpRequest;
import org.apache.coyote.http11.HttpResponse;

import java.util.Objects;
import java.util.UUID;

public class LoginController extends AbstractController {
    private static final String JSESSIONID = "JSESSIONID";
    private static final String INDEX_PATH = "/index.html";
    private static final String UNAUTHORIZED_PATH = "/401.html";

    @Override
    protected void doPost(final HttpRequest request, final HttpResponse response) throws Exception {
        String account = request.getBodyValue("account");
        User user = InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new UserNotFoundException(account));
        String password = request.getBodyValue("password");

        if (user.checkPassword(password)) {
            String uuid = UUID.randomUUID().toString();
            Session session = request.getSession();
            session.setAttribute(uuid, user);

            Cookie cookie = Cookie.of(JSESSIONID, uuid);
            response.setCookie(cookie);
            response.sendRedirect(INDEX_PATH);
            return;
        }

        response.sendRedirect(UNAUTHORIZED_PATH);
    }

    @Override
    protected void doGet(final HttpRequest request, final HttpResponse response) throws Exception {
        Cookie cookie = request.getCookie(JSESSIONID);
        Session session = request.getSession();
        if (cookie != null && Objects.nonNull(session.getAttribute(cookie.getValue()))) {
            response.sendRedirect(INDEX_PATH);
            return;
        }

        response.sendResource("/login.html");
    }
}
