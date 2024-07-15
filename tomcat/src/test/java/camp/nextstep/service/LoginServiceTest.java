package camp.nextstep.service;

import camp.nextstep.exception.UnauthorizedException;
import camp.nextstep.exception.UserNotFoundException;
import camp.nextstep.model.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LoginService 클래스는")
class LoginServiceTest {

    private LoginService loginService = new LoginService();

    @DisplayName("로그인이 되어 있을 경우 true를 반환한다.")
    @Test
    void login() throws IOException {
        // given
        Request request = new Request(new BufferedReader(new StringReader("GET /login HTTP/1.1\n" +
            "Cookie: JSESSIONID=1234\n" +
            "\n")));
        Session session = SessionManager.getSession(request.getCookies());
        session.setAttribute("user", "test");
        // when
        boolean isLoginUser = loginService.isLoginUser(request);
        // then
        assertTrue(isLoginUser);
    }

    @DisplayName("로그인 시 존재하지 않은 사용자일 경우 예외를 발생시킨다.")
    @Test
    void authenticateUser_notFoundUser() throws IOException {
        // given
        Request request = new Request(new BufferedReader(new StringReader("POST /login HTTP/1.1\n" +
            "Content-Length: 23\n" +
            "Cookie: JSESSIONID=1234\n" +
            "\n" +
            "account=test&password=1234")));
        // when
        assertThrows(UserNotFoundException.class, () -> loginService.authenticateUser(request));
    }

    @DisplayName("로그인 시 패스워드가 일치하지 않을 경우 예외를 발생시킨다.")
    @Test
    void authenticateUser_invalidPassword() throws IOException {
        // given
        Request request = new Request(new BufferedReader(new StringReader("POST /login HTTP/1.1\n" +
            "Content-Length: 23\n" +
            "Cookie: JSESSIONID=1234\n" +
            "\n" +
            "account=gugu&password=1234")));
        // when
        assertThrows(UnauthorizedException.class, () -> loginService.authenticateUser(request));
    }

    @DisplayName("로그인 시 성공하면 세션에 사용자 정보를 저장한다.")
    @Test
    void authenticateUser() throws IOException {
        // given
        Request request = new Request(new BufferedReader(new StringReader("POST /login HTTP/1.1\n" +
            "Content-Length: 30\n" +
            "Cookie: JSESSIONID=1234\n" +
            "\n" +
            "account=gugu&password=password")));
        // when
        loginService.authenticateUser(request);
        // then
        Session session = SessionManager.getSession(request.getCookies());
        User user = (User) session.getAttribute("user");
        assertEquals("gugu", user.getAccount());
    }
}
