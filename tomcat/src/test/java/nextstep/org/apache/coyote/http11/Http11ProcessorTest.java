package nextstep.org.apache.coyote.http11;

import camp.nextstep.model.User;
import org.apache.coyote.http11.InMemorySessionRepository;
import org.junit.jupiter.api.DisplayName;
import support.StubSocket;
import org.apache.coyote.http11.Http11Processor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class Http11ProcessorTest {
    @DisplayName("Http11Processor 프로세스를 수행하면 응답이 반환된다")
    @Test
    void process() {
        // given
        final var socket = new StubSocket();
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5564 ",
                "");

        assertThat(socket.output()).contains(expected);
    }

    @DisplayName("/index.html로 요청하면 responseBody에 index.html 파일의 내용이 추가된다.")
    @Test
    void index() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /index.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        String responseBody = getResponseBody("static/index.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5564 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                responseBody);

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("/css/styles.css로 요청하면 responseBody에 styles.css 파일의 내용이 추가된다.")
    @Test
    void stylesCss() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /css/styles.css HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        String responseBody = getResponseBody("static/css/styles.css");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 211991 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                responseBody);

        assertThat(socket.output()).isEqualTo(expected);
    }

    private String getResponseBody(String resourcePath) throws IOException {
        final URL resource = getClass().getClassLoader().getResource(resourcePath);
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }

    @DisplayName("/login + GET 요청 시, 쿠키가 없으면 login.html이 응답된다")
    @Test
    void getLogin() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /login HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        String responseBody = getResponseBody("static/login.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 3797 ",
                "",
                responseBody);

        assertThat(socket.output()).isEqualTo(expected);
    }


    @DisplayName("/login + GET 요청 시, 쿠키가 있어도 세션에 없으면 login.html이 응답된다")
    @Test
    void getLogin2() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /login HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: JSESSIONID=uuid2345 ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        String responseBody = getResponseBody("static/login.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 3797 ",
                "",
                responseBody);

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("/login + GET 요청 시, 쿠키가 있고 세션이 저장돼 있으면 /index.html로 리다이렉트")
    @Test
    void getLoginFail() {
        // given
        String cookie = "uuid";
        InMemorySessionRepository.save(cookie, new User(2L, "account", "password", "email"));
        final String httpRequest = String.join("\r\n",
                "GET /login HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: JSESSIONID=uuid ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Content-Length: 0 ",
                "Location: /index.html ",
                "",
                "");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("/login + POST 요청은 성공 시 302 FOUND를 응답하고 Location으로 /index.html을 제공한다")
    @Test
    void postLogin() {
        // given
        String body = "account=gugu&password=password";
        final String httpRequest = String.join("\r\n",
                "POST /login HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Content-Length: %d ".formatted(body.getBytes().length),
                "Connection: keep-alive ",
                "",
                body);

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        assertThat(socket.output()).contains("Set-Cookie: JSESSIONID=", "Content-Length: 0", "Location: /index.html");
    }

    @DisplayName("/login + POST 요청은 실패 시 302 FOUND를 응답하고 Location으로 /401.html을 제공한다")
    @Test
    void postLoginFail() {
        // given
        String body = "account=gugu&password=passwordddd!";
        final String httpRequest = String.join("\r\n",
                "POST /login HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Content-Length: %d ".formatted(body.getBytes().length),
                "Connection: keep-alive ",
                "",
                body);

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Content-Length: 0 ",
                "Location: /401.html ",
                "",
                "");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("/register + POST 요청은 성공 시 302 FOUND를 응답하고 Location으로 /index.html을 제공한다")
    @Test
    void register() {
        // given
        String body = "account=gugu&password=password";
        final String httpRequest = String.join("\r\n",
                "POST /register HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Content-Length: %d ".formatted(body.getBytes().length),
                "Connection: keep-alive ",
                "",
                body);

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Content-Length: 0 ",
                "Location: /index.html ",
                "",
                "");

        assertThat(socket.output()).isEqualTo(expected);
    }
}
