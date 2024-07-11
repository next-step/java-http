package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.domain.http.HttpCookie;
import camp.nextstep.domain.http.HttpHeaders;
import camp.nextstep.domain.http.HttpStatus;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.request.HttpRequestBody;
import camp.nextstep.domain.http.request.RequestLine;
import camp.nextstep.domain.http.response.HttpResponse;
import camp.nextstep.domain.session.Session;
import camp.nextstep.domain.session.SessionManager;
import camp.nextstep.model.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LoginControllerTest {

    private final LoginController loginController = new LoginController();

    @Test
    void login경로의_static정보를_반환한다() throws IOException {
        HttpRequest request = new HttpRequest(
                new RequestLine("GET /login HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        URL resource = getClass().getClassLoader().getResource("static/login.html");
        String expected = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        HttpResponse actual = loginController.doGet(request);
        assertThat(actual.getResponseBody()).isEqualTo(expected);
    }

    @Test
    void login경로_요청_시_이미_로그인된_유저면_index로_리다이랙한다() {
        Session session = new Session("login_user_key");
        SessionManager.add(session);
        HttpRequest request = new HttpRequest(
                new RequestLine("GET /login HTTP/1.1"),
                new HttpHeaders(),
                HttpCookie.sessionCookie(session),
                new HttpRequestBody()
        );

        HttpResponse actual = loginController.doGet(request);
        assertAll(
                () -> assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.FOUND),
                () -> assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/index.html"))
        );
    }

    @Test
    void post요청으로_로그인한다() {
        InMemoryUserRepository.save(new User("post_login_jinyoung", "1234", "jinyoungchoi95@gmail.com"));
        HttpRequest request = new HttpRequest(
                new RequestLine("POST /login HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                HttpRequestBody.from("account=post_login_jinyoung&password=1234")
        );
        HttpResponse actual = loginController.doPost(request);
        assertAll(
                () -> assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/index.html")),
                () -> assertThat(actual.getHttpCookie().getCookies()).containsKey("JSESSIONID")
        );
    }

    @Test
    void post요청으로_로그인실패하면_401을_반환한다() {
        HttpRequest request = new HttpRequest(
                new RequestLine("POST /login HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                HttpRequestBody.from("account=gugu&password=error")
        );
        HttpResponse actual = loginController.doPost(request);
        assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/401.html"));
    }
}
