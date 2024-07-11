package camp.nextstep.controller;

import camp.nextstep.domain.http.HttpCookie;
import camp.nextstep.domain.http.HttpHeaders;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.request.HttpRequestBody;
import camp.nextstep.domain.http.request.RequestLine;
import camp.nextstep.domain.http.response.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RootControllerTest {

    private final RootController rootController = new RootController();

    @Test
    void root경로의_static정보를_반환한다() throws IOException {
        HttpRequest request = new HttpRequest(
                new RequestLine("GET / HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );

        HttpResponse actual = rootController.doGet(request);
        assertThat(actual.getResponseBody()).isEqualTo("Hello world!");
    }

    @Test
    void post요청은_404을_반환한다() {
        HttpRequest request = new HttpRequest(
                new RequestLine("POST / HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        HttpResponse actual = rootController.doPost(request);
        assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/404.html"));
    }
}
