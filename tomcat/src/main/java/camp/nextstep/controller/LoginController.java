package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
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
    private static final String LOGIN_PATH = "/login";
    private static final String ACCOUNT_KEY = "account";

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        if (request.containsSessionId()) {
            String sessionId = request.getSessionId();
            Optional<Session> session = SessionManager.findSession(sessionId);
            if (session.isPresent()) {
                return new HttpResponse(
                        HttpStatus.OK,
                        MimeType.HTML,
                        FileFinder.find("/index.html"));
            }
        }
        MimeType mimeType = MimeType.HTML;
        return new HttpResponse(
                HttpStatus.OK,
                mimeType,
                FileFinder.find(LOGIN_PATH + mimeType.getFileExtension()));
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        RequestBody requestBody = request.getRequestBody();
        Optional<User> optionalUser = InMemoryUserRepository.findByAccount(requestBody.get(ACCOUNT_KEY));
        if (optionalUser.isPresent() && optionalUser.get().checkPassword(requestBody.get("password"))) {
            HttpResponse httpResponse = new HttpResponse(
                    HttpStatus.FOUND,
                    MimeType.HTML,
                    FileFinder.find("/index.html"));
            Session session = new Session();
            session.setAttribute("user", optionalUser.get());
            SessionManager.add(session);
            httpResponse.addCookie(session.getId());
            return httpResponse;
        }
        return new HttpResponse(
                HttpStatus.UNAUTHORIZED,
                MimeType.HTML,
                FileFinder.find("/401.html"));

    }
}
