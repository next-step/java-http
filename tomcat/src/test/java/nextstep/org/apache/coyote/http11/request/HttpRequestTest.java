package nextstep.org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.model.QueryStrings;
import org.apache.coyote.http11.request.model.RequestBodies;
import org.apache.coyote.http11.request.model.RequestHeaders;
import org.apache.coyote.http11.request.parser.HttpRequestParser;
import org.apache.coyote.http11.session.Session;
import org.apache.coyote.http11.session.SessionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class HttpRequestTest {
    @Test
    @DisplayName("Http Get 요청을 통해 HttpRequest 를 생성할 수 있다")
    void httpRequest_get_create_test() {
        final String httpRequestString = "GET /login?account=gugu&password=password HTTP/1.1 \r\n" +
                "Host: localhost:8080 \r\n" +
                "Connection: keep-alive \r\n" +
                "\r\n";
        final InputStream inputStream = new ByteArrayInputStream(httpRequestString.getBytes());

        final HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));

        assertSoftly(softly -> {
            softly.assertThat(httpRequest.isGet()).isTrue();
            softly.assertThat(httpRequest.getUrlPath()).isEqualTo("/login");
            softly.assertThat(httpRequest.getHeaders()).isEqualTo(new RequestHeaders(Map.of("Host", "localhost:8080 ", "Connection", "keep-alive ")));
            softly.assertThat(httpRequest.getQueryStrings().getQueryString()).isEqualTo(new QueryStrings(Map.of("account", "gugu", "password", "password")).getQueryString());
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

        final HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));

        assertSoftly(softly -> {
            softly.assertThat(httpRequest.isPost()).isTrue();
            softly.assertThat(httpRequest.getUrlPath()).isEqualTo("/register");
            softly.assertThat(httpRequest.getHeaders()).isEqualTo(new RequestHeaders(Map.of("Host", "localhost:8080 ", "Connection", "keep-alive ","Content-Length","50 ")));
            softly.assertThat(httpRequest.getRequestBodies().getBody()).isEqualTo(new RequestBodies(Map.of("account", "test", "password", "password", "email", "test@test.com")).getBody());
        });
    }

    @Test
    @DisplayName("Http 요청에 Session 정보를 가져올 수 있다.")
    void httpRequest_session_test() throws IOException {
        final Session session = new Session("session");
        SessionManager.getInstance().add(session);
        final String httpRequestString = "GET /index.html HTTP/1.1 \r\n" +
                "Host: localhost:8080 \r\n" +
                "Connection: keep-alive \r\n" +
                "Content-Length: 50 \r\n" +
                "Cookie: JSESSIONID=session \r\n" +
                "\r\n";
        final InputStream inputStream = new ByteArrayInputStream(httpRequestString.getBytes());

        final HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));

        assertThat(httpRequest.getSession().getAttributes()).isEqualTo(new Session("session").getAttributes());
    }
}
