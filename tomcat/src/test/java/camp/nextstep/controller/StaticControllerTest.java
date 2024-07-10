package camp.nextstep.controller;

import camp.nextstep.domain.http.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StaticControllerTest {

    private final StaticController staticController = new StaticController();

    @Test
    void 요청된_path의_static정보를_반환한다() throws IOException {
        HttpRequest request = new HttpRequest(
                new RequestLine("GET /css/styles.css HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        String expected = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        HttpResponse actual = staticController.doGet(request);
        assertThat(actual.getResponseBody()).isEqualTo(expected);
    }

    @Test
    void post요청은_404을_반환한다() {
        HttpRequest request = new HttpRequest(
                new RequestLine("GET /css/styles.css HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        HttpResponse actual = staticController.doPost(request);
        assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/404.html"));
    }
}
