package camp.nextstep.controller;

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

import static org.assertj.core.api.Assertions.assertThat;

class PathResponseGeneratorTest {

    private final PathResponseGenerator pathResponseGenerator = PathResponseGenerator.getInstance();

    @Test
    void path로_response를_생성한다() throws IOException {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /login HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        URL resource = getClass().getClassLoader().getResource("static/login.html");
        String expected = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        HttpResponse actual = pathResponseGenerator.handlePath(httpRequest);
        assertThat(actual.getResponseBody()).isEqualTo(expected);
    }

    @Test
    void static_path로_response를_생성한다() throws IOException {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /login.html HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        URL resource = getClass().getClassLoader().getResource("static/login.html");
        String expected = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        HttpResponse actual = pathResponseGenerator.handleStaticPath(httpRequest);
        assertThat(actual.getResponseBody()).isEqualTo(expected);
    }
}
