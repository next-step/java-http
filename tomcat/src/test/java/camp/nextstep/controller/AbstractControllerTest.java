package camp.nextstep.controller;

import camp.nextstep.domain.http.HttpCookie;
import camp.nextstep.domain.http.HttpHeaders;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.request.HttpRequestBody;
import camp.nextstep.domain.http.request.RequestLine;
import camp.nextstep.domain.http.response.HttpResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractControllerTest {

    @Test
    void httpRequest가_get을_지원하지_않으면_404를_반환한다() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /docs/index.html HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        HttpResponse actual = new AbstractController() {
        }.doGet(httpRequest);
        assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/404.html"));
    }

    @Test
    void httpRequest가_post를_지원하지_않으면_404를_반환한다() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("POST /docs/index.html HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        HttpResponse actual = new AbstractController() {
        }.doGet(httpRequest);
        assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/404.html"));
    }

    @Test
    void httpRequest가_지원하지_않는_method인_경우_404를_반환한다() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("PUT /docs/index.html HTTP/1.1"),
                new HttpHeaders(),
                new HttpCookie(),
                new HttpRequestBody()
        );
        HttpResponse actual = new AbstractController() {
        }.doGet(httpRequest);
        assertThat(actual.getHttpHeaders().getHeaders()).contains(Map.entry("Location", "/404.html"));
    }
}
