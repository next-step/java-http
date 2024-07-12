package camp.nextstep.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class HttpRequestTest {

    @Test
    @DisplayName("Http Get 요청을 통해 HttpRequest 를 생성할 수 있다")
    void httpRequest_get_create_test() throws IOException {
        final String httpRequestString = "GET /login?account=gugu&password=password HTTP/1.1 \r\n" +
                "Host: localhost:8080 \r\n" +
                "Connection: keep-alive \r\n" +
                "\r\n";
        final InputStream inputStream = new ByteArrayInputStream(httpRequestString.getBytes());

        final HttpRequest httpRequest = new HttpRequest(inputStream);

        assertSoftly(softly -> {
            softly.assertThat(httpRequest.isGetMethod()).isTrue();
            softly.assertThat(httpRequest.getPath()).isEqualTo(new HttpPath("/login"));
            softly.assertThat(httpRequest.getHeaders()).isEqualTo(new HttpHeaders(Map.of("Host", "localhost:8080", "Connection", "keep-alive")));
            softly.assertThat(httpRequest.getQueryParameters()).isEqualTo(new QueryParameters("account=gugu&password=password"));
        });
    }

    @Test
    @DisplayName("Http Post 요청을 통해 HttpRequest 를 생성할 수 있다")
    void httpRequest_post_create_test() throws IOException {
        final String httpRequestString = "POST /register HTTP/1.1 \r\n" +
                "Host: localhost:8080 \r\n" +
                "Connection: keep-alive \r\n" +
                "Content-Length: 50 \r\n" +
                "\r\n" +
                "account=test&password=password&email=test@test.com";
        final InputStream inputStream = new ByteArrayInputStream(httpRequestString.getBytes());

        final HttpRequest httpRequest = new HttpRequest(inputStream);

        assertSoftly(softly -> {
            softly.assertThat(httpRequest.isPostMethod()).isTrue();
            softly.assertThat(httpRequest.getPath()).isEqualTo(new HttpPath("/register"));
            softly.assertThat(httpRequest.getHeaders()).isEqualTo(new HttpHeaders(Map.of("Host", "localhost:8080", "Connection", "keep-alive", "Content-Length", "50")));
            softly.assertThat(httpRequest.getRequestBody()).isEqualTo(new RequestBody("account=test&password=password&email=test@test.com"));
        });
    }

    @Test
    @DisplayName("Http 요청에 Session 정보를 가져올 수 있다.")
    void httpRequest_session_test() throws IOException {
        final HttpSession existingSession = new HttpSession("session");
        HttpSessionManager.add(existingSession);
        final String httpRequestString = "GET /index.html HTTP/1.1 \r\n" +
                "Host: localhost:8080 \r\n" +
                "Connection: keep-alive \r\n" +
                "Content-Length: 50 \r\n" +
                "Cookie: JSESSIONID=session \r\n" +
                "\r\n";
        final InputStream inputStream = new ByteArrayInputStream(httpRequestString.getBytes());

        final HttpRequest httpRequest = new HttpRequest(inputStream);

        assertThat(httpRequest.getSession()).isEqualTo(new HttpSession("session"));
    }
}
