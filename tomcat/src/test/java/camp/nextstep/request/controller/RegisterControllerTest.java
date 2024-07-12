package camp.nextstep.request.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.HttpRequestHeaderParser;
import org.apache.coyote.http11.HttpRequestParser;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;
import org.apache.coyote.http11.model.constant.HttpStatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RegisterControllerTest {
    @DisplayName("회원가입 페이지를 GET 메서드로 요청하면 register.html 페이지가 조회된다.")
    @Test
    void requestGetMethodRegister() throws Exception {
        // given
        final List<String> request = List.of(
                "GET /register.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final HttpRequest httpRequest = HttpRequestParser.getInstance()
                .parse(HttpRequestHeaderParser.getInstance().parse(request), "");

        final Controller registerController = RegisterController.getInstance();
        final URL resource = getClass().getClassLoader().getResource("static/register.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 4319 ",
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        // when
        final HttpResponse response = registerController.service(httpRequest);

        // then
        assertThat(response.buildResponse()).isEqualTo(expected);
    }

    @DisplayName("회원가입 페이지를 POST 메서드로 요청하면 회원가입되고, index.html 페이지로 리다이렉트.")
    @Test
    void requestPostMethodRegisterRedirectIndex() throws Exception {
        // given
        final List<String> request = List.of(
                "POST /register.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Length: 80 ",
                "Content-Type: application/x-www-form-urlencoded ",
                "Accept: */* ",
                "",
                "account=gugu&password=password&email=hkkang%40woowahan.com");

        final HttpRequest httpRequest = HttpRequestParser.getInstance()
                .parse(HttpRequestHeaderParser.getInstance().parse(request), "account=gugu&password=password&email=hkkang%40woowahan.com");

        final Controller registerController = RegisterController.getInstance();
        var expected = String.join("\r\n",
                "HTTP/1.1 302 FOUND ",
                "Location: /index.html ",
                "",
                "");

        // when
        final HttpResponse response = registerController.service(httpRequest);
        final Optional<User> user = InMemoryUserRepository.findByAccount("gugu");

        // then
        assertAll(
                () -> assertThat(response.buildResponse()).isEqualTo(expected),
                () -> assertThat(user.isPresent()).isTrue(),
                () -> assertThat(user.get().getAccount()).isEqualTo("gugu")
        );
    }

    @DisplayName("회원가입 페이지를 GET,POST 이외의 메서드로 요청하면 404.html 페이지로 리다이렉트.")
    @Test
    void requestOtherMethodRegisterRedirectIndex() throws Exception {
        // given
        final List<String> request = List.of(
                "PUT /register.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final HttpRequest httpRequest = HttpRequestParser.getInstance()
                .parse(HttpRequestHeaderParser.getInstance().parse(request), "");

        final Controller registerController = RegisterController.getInstance();
        var expected = String.join("\r\n",
                "HTTP/1.1 302 FOUND ",
                "Location: /404.html ",
                "",
                "");

        // when
        final HttpResponse response = registerController.service(httpRequest);

        // then
        assertThat(response.code()).isEqualTo(HttpStatusCode.FOUND);
        assertThat(response.buildResponse()).isEqualTo(expected);
    }
}
