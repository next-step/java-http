package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UnauthorizedUserException;
import camp.nextstep.model.User;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.request.RequestBody;
import org.apache.coyote.response.FileFinder;
import org.apache.coyote.response.HttpResponse;
import org.apache.coyote.response.HttpStatus;
import org.apache.coyote.response.MimeType;
import org.apache.coyote.session.Session;
import org.apache.coyote.session.SessionManager;

import java.util.Optional;

public class LoginController extends AbstractController {
    private static final String ACCOUNT_KEY = "account";

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        if (isAlreadyLoggedIn(request)) {
            return new HttpResponse(
                    HttpStatus.OK,
                    MimeType.HTML,
                    FileFinder.find("/index.html"));
        }
        return new HttpResponse(
                HttpStatus.OK,
                MimeType.HTML,
                FileFinder.find("/login.html"));
    }

    private boolean isAlreadyLoggedIn(HttpRequest request) {
        if (!request.containsSessionId()) {
            return false;
        }
        Optional<Session> session = SessionManager.findSession(request.getSessionId());
        return session.isPresent();
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        try {
            User user = getAuthorizedUser(request);
            Session session = addSession(user);
            HttpResponse httpResponse = new HttpResponse(
                    HttpStatus.FOUND,
                    MimeType.HTML,
                    FileFinder.find("/index.html"));
            httpResponse.addCookie(session.getId());
            return httpResponse;
        } catch (UnauthorizedUserException e) {
            return HttpResponse.unauthorized();
        }
    }

    private User getAuthorizedUser(HttpRequest request) {
        RequestBody requestBody = request.getRequestBody();
        return InMemoryUserRepository.findByAccount(requestBody.get(ACCOUNT_KEY))
                .filter(user -> user.checkPassword(requestBody.get("password")))
                .orElseThrow(UnauthorizedUserException::new);
    }

    private Session addSession(User user) {
        Session session = new Session();
        session.setAttribute("user", user);
        SessionManager.add(session);
        return session;
    }
}
