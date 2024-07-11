package camp.nextstep.controller;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.domain.http.HttpCookie;
import camp.nextstep.domain.http.HttpHeaders;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.request.HttpRequestBody;
import camp.nextstep.domain.http.request.RequestLine;
import camp.nextstep.domain.http.response.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RegisterControllerTest {

    private final RegisterController registerController = new RegisterController();

    @Test
    void register경로의_static정보를_반환한다() throws IOException {
        HttpRequest request = new HttpRequest(
                new RequestLine("GET /register HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        URL resource = getClass().getClassLoader().getResource("static/register.html");
        String expected = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        HttpResponse actual = registerController.doGet(request);
        assertThat(actual.getResponseBody()).isEqualTo(expected);
    }

    @Test
    void post요청으로_회원가입한다() {
        HttpRequest request = new HttpRequest(
                new RequestLine("POST /register HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                HttpRequestBody.from("account=jinyoung&password=1234&email=jinyoungchoi95@gmail.com")
        );
        HttpResponse actual = registerController.doPost(request);
        assertAll(
                () -> assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/index.html")),
                () -> assertThat(actual.getHttpCookie().getCookies()).containsKey("JSESSIONID"),
                () -> assertThat(InMemoryUserRepository.findByAccount("jinyoung")).isNotEmpty()
        );
    }
}
