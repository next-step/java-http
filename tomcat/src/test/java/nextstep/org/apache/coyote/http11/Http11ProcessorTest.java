package nextstep.org.apache.coyote.http11;

import camp.nextstep.http.domain.HttpSession;
import camp.nextstep.http.domain.HttpSessionManager;
import camp.nextstep.model.User;
import org.apache.coyote.http11.Http11Processor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import support.StubSocket;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.given;

class Http11ProcessorTest {

    private static final String HTTP_GET_REQUEST_TEMPLATE =
            "GET %s HTTP/1.1 \r\n" +
                    "Host: localhost:8080 \r\n" +
                    "Connection: keep-alive \r\n" +
                    "\r\n";

    private static final String HTTP_POST_REQUEST_TEMPLATE =
            "POST %s HTTP/1.1 \r\n" +
                    "Host: localhost:8080 \r\n" +
                    "Connection: keep-alive \r\n" +
                    "Content-Length: %d \r\n" +
                    "\r\n" +
                    "%s";
    public static final UUID mockedUUID = UUID.fromString("656cef62-e3c4-40bc-a8df-94732920ed46");

    private static MockedStatic<UUID> uuidMockedStatic;

    @BeforeAll
    static void beforeAll() {
        uuidMockedStatic = Mockito.mockStatic(UUID.class);
        given(UUID.randomUUID()).willReturn(mockedUUID);
    }

    @AfterAll
    public static void afterAll() {
        uuidMockedStatic.close();
    }

    @Test
    @DisplayName("root 페이지에 접근할 수 있다")
    void process() {
        // given
        final var socket = new StubSocket();
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 12 ",
                "",
                "Hello world!");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("index 페이지에 접근할 수 있다")
    void process_index() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/index.html");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5670 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("없는 페이지 요청시 404html 이 보여진다")
    void process_wrong_page() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/wrong_page.html");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/404.html");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 2477 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("css 요청을 처리할 수 있다")
    void process_css() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/css/styles.css");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 223255 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("js 요청을 처리할 수 있다")
    void process_js() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/js/scripts.js");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/js/scripts.js");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Content-Type: text/javascript;charset=utf-8 ",
                "Content-Length: 1002 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("로그인 post 요청을 처리할 수 있다")
    void process_login_post() {
        // given
        final String body = "account=gugu&password=password";
        final String httpRequest = String.format(HTTP_POST_REQUEST_TEMPLATE, "/login", body.length(), body);
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Location: /index.html ",
                "",
                "");
        assertSoftly(softly -> {
            softly.assertThat(socket.output()).isEqualTo(expected);
            softly.assertThat(HttpSessionManager.computeIfAbsent(mockedUUID.toString())).isNotNull();
        });
    }

    @Test
    @DisplayName("로그인 get 요청시 로그인이 안되어있으면 login.html 로 이동한다")
    void process_login_get_without_login() throws IOException {
        // given
        HttpSessionManager.clear();
        final String httpRequest = "GET /login HTTP/1.1 \r\n" +
                "Host: localhost:8080 \r\n" +
                "Connection: keep-alive \r\n" +
                "\r\n";
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/login.html");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 3863 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("로그인 get 요청시 로그인이 되어있으면 index.html 로 리다이렉트한다")
    void process_login_get_with_login() {
        // given
        final HttpSession session = new HttpSession(mockedUUID.toString());
        session.setAttribute("user", new User("test", "password", "email"));
        HttpSessionManager.add(session);
        final String httpRequest = "GET /login HTTP/1.1 \r\n" +
                String.format("Cookie: JSESSIONID=%s \r\n", mockedUUID) +
                "Host: localhost:8080 \r\n" +
                "Connection: keep-alive \r\n" +
                "\r\n";
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Location: /index.html ",
                "",
                "");
        assertSoftly(softly -> {
            softly.assertThat(socket.output()).isEqualTo(expected);
            softly.assertThat(HttpSessionManager.computeIfAbsent(mockedUUID.toString())).isNotNull();
        });
    }

    @Test
    @DisplayName("로그인 실패시 401.html 로 리다이렉트한다")
    void process_login_failed() {
        // given
        final String body = "account=gugu&password=wrong";
        final String httpRequest = String.format(HTTP_POST_REQUEST_TEMPLATE, "/login", body.length(), body);
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Location: /401.html ",
                "",
                "");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("회원가입 GET 요청시 회원가입 페이지로 이동한다")
    void process_register_get() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/register");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/register.html");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 4391 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("회원가입 Post 요청을 처리하면 index.html 로 리다이렉트한다")
    void process_register_post() {
        // given
        final String body = "account=test&password=password&email=test@test.com";
        final String httpRequest = String.format(HTTP_POST_REQUEST_TEMPLATE, "/register", body.length(), body);
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Location: /index.html ",
                "",
                "");

        assertThat(socket.output()).isEqualTo(expected);
    }
}
