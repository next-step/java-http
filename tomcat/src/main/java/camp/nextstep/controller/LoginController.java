package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.http.HttpCookie;
import camp.nextstep.http.HttpRequest;
import camp.nextstep.http.HttpResponse;
import camp.nextstep.http.QueryString;
import camp.nextstep.model.User;
import camp.nextstep.session.Session;
import camp.nextstep.session.SessionManager;

import java.util.NoSuchElementException;

public class LoginController extends AbstractController{

    private static final String INDEX_PATH = "/index.html";

    @Override
    protected HttpResponse doPost(HttpRequest request) throws Exception {
        return super.doPost(request);
    }

    @Override
    protected HttpResponse doGet(HttpRequest request) throws Exception {
        return handleLoginRequest(request);
    }

    private HttpResponse handleLoginRequest(HttpRequest httpRequest) {

        if (httpRequest.getHeaders().isCookieExisted()) {
            return handleExistingSession(httpRequest);
        }

        return processLogin(httpRequest);
    }

    private HttpResponse handleExistingSession(HttpRequest httpRequest) {
            String sessionId = httpRequest.getHeaders().getValueByKey("Cookie").split("=")[1];
            if (SessionManager.findSession(sessionId) == null) {
                return HttpResponse.redirect("/401.html");
            }
            return HttpResponse.redirect(INDEX_PATH);
    }

    private HttpResponse processLogin(HttpRequest httpRequest) {
        QueryString queryString = httpRequest.getRequestLine().getPath().getQueryString();
        String userId = queryString.getValueByKey("account");
        String password = queryString.getValueByKey("password");

        try {
            User user = checkUserInformation(userId, password);
            Session session = createAndSaveSession(user);
            return HttpResponse.redirect(INDEX_PATH).addCookie(HttpCookie.cookieSession(session));
        } catch (NoSuchElementException e) {
            return HttpResponse.redirect("/401.html");
        }
    }

    private Session createAndSaveSession(User user) {
        Session session = Session.createNewSession();
        session.setAttribute("user", user);
        SessionManager.add(session);
        return session;
    }

    private User checkUserInformation(String userId, String password) {

        if (userId == null || password == null) {
            throw new NoSuchElementException("사용자 정보가 올바르지 않습니다.");
        }

        final User user = InMemoryUserRepository.findByAccount(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        if (!user.checkPassword(password)) {
            throw new NoSuchElementException("잘못된 비밀번호입니다.");
        }
        return user;
    }
}
