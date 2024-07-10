package camp.nextstep.request;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestParserTest {

    private final RequestParser requestParser = new RequestParser();

    @Test
    void parseGetRequest() {
        String requestLineString = "GET /docs/index.html HTTP/1.1";
        Request request = requestParser.parse(requestLineString);
        RequestLine requestLine = request.getRequestLine();

        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parsePostRequest() {
        String requestLineString = "POST /docs/index.html HTTP/1.1";
        Request request = requestParser.parse(requestLineString);
        RequestLine requestLine = request.getRequestLine();

        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getQueryParameter("somekey")).isEqualTo(null);
        assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parseQueryString() {
        String requestLineString = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        Request request = requestParser.parse(requestLineString);
        RequestLine requestLine = request.getRequestLine();
        QueryParameters queryParameters = requestLine.getQueryParameters();

        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET);
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
        Request request = requestParser.parse(requestLineString);
        QueryParameters queryParameters = request
                .getRequestLine()
                .getQueryParameters();

        assertThat(queryParameters.get("userId")).isEqualTo("javajigi");
        assertThat(queryParameters.getAll("userId")).isEqualTo(List.of("javajigi", "abc"));
    }
}
