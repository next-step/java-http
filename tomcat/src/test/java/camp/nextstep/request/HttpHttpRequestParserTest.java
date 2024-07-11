package camp.nextstep.request;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpHttpRequestParserTest {

    private final HttpRequestParser requestParser = new HttpRequestParser();

    @Test
    void parseGetRequest() {
        String requestLineString = "GET /docs/index.html HTTP/1.1";
        HttpRequestLine requestLine = HttpRequestLine.parse(requestLineString);

        assertThat(requestLine.getMethod()).isEqualTo(HttpRequestMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parsePostRequest() {
        String requestLineString = "POST /docs/index.html HTTP/1.1";
        HttpRequestLine requestLine = HttpRequestLine.parse(requestLineString);

        assertThat(requestLine.getMethod()).isEqualTo(HttpRequestMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getQueryParameter("somekey")).isEqualTo(null);
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseQueryString() {
        String requestLineString = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        HttpRequestLine requestLine = HttpRequestLine.parse(requestLineString);
        HttpQueryParameters queryParameters = requestLine.getQueryParameters();

        assertThat(requestLine.getMethod()).isEqualTo(HttpRequestMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(queryParameters.get("userId")).isEqualTo("javajigi");
        assertThat(queryParameters.get("password")).isEqualTo("password");
        assertThat(queryParameters.get("name")).isEqualTo("JaeSung");
        assertThat(queryParameters.get("somekey")).isEqualTo(null);
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseQueryString2() {
        String requestLineString = "GET /users?userId=javajigi&userId=abc HTTP/1.1";
        HttpRequestLine requestLine = HttpRequestLine.parse(requestLineString);
        HttpQueryParameters queryParameters = requestLine.getQueryParameters();

        assertThat(queryParameters.get("userId")).isEqualTo("javajigi");
        assertThat(queryParameters.getAll("userId")).isEqualTo(List.of("javajigi", "abc"));
    }
}