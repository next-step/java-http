package camp.nextstep.handler;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.catalina.manager.SessionManager;
import org.apache.coyote.support.ResourceFinder;
import org.apache.coyote.http11.constants.HttpStatus;
import org.apache.coyote.http11.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class LoginHandler extends AbstractController {

    private static final String LOGIN_RESOURCE_PATH = "/login.html";
    private static final String REDIRECT_PATH = "/index.html";
    private static final String UNAUTHORIZED_RESOURCE_PATH = "/401.html";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        final String account = request.getBody().get("account");
        final String password = request.getBody().get("password");
        Optional<User> userOptional = InMemoryUserRepository.findByAccount(account);

        if(userOptional.isEmpty() || !userOptional.get().checkPassword(password)) {
            sendResponse(UNAUTHORIZED_RESOURCE_PATH, response, HttpStatus.UNAUTHORIZED);
        }

        /* 세션 설정 */
        if(request.getCookie() != null) {
            Optional<HttpSession> sessionOptional =
                    SessionManager.getInstance().findSession(request.getCookie().JSessionId());
            sessionOptional.ifPresent(SessionManager.getInstance()::remove);
        }
        final HttpSession session = SessionManager.getInstance().add(new HttpSession());
        session.setAttribute("user", userOptional.get());
        response.setJSessionId(session.getId());

        sendResponse(REDIRECT_PATH, response, HttpStatus.REDIRECT);
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        if(existSession(request.getCookie())) {
            sendResponse(REDIRECT_PATH, response, HttpStatus.REDIRECT);
        }

        sendResponse(LOGIN_RESOURCE_PATH, response, HttpStatus.SUCCESS);
    }

    /**
     * 응답 생성
     *
     * @param resourcePath 정적 리소스 파일 path
     * @param response http 응답
     * @param status http 상태 코드
     * @throws IOException
     */
    private static void sendResponse(String resourcePath, HttpResponse response, HttpStatus status) throws IOException {
        final URL resource = ResourceFinder.findResource(resourcePath);
        final String content = ResourceFinder.findContent(resource);
        response.setStatus(status);
        response.setContent(content);
        response.setContentType(ContentType.TEXT_HTML);
        response.send();
    }

    /**
     * 세션 여부 확인
     * 세션이 존재할 경우 인증없이 redirect page 를 호출하기 위해 검사
     *
     * @param cookie 쿠키
     * @return
     */
    private boolean existSession(HttpCookie cookie) {
        if(cookie == null || !cookie.hasJSessionId()) {
            return false;
        }

        return SessionManager.getInstance().findSession(cookie.JSessionId()).isPresent();
    }
}
