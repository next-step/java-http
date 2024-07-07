package camp.nextstep.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestParserTest {

    private final RequestParser requestParser = new RequestParser();

    @Test
    void parseGetRequest() {
        String requestLine = "GET /docs/index.html HTTP/1.1";

        Request request = requestParser.parse(requestLine);

        assertThat(request.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(request.getPath()).isEqualTo("/docs/index.html");
        assertThat(request.getHttpVersion()).isEqualTo("HTTP/1.1");
    }

    @Test
    void parsePostRequest() {
        String requestLine = "POST /docs/index.html HTTP/1.1";

        Request request = requestParser.parse(requestLine);

        assertThat(request.getMethod()).isEqualTo(RequestMethod.POST);
        assertThat(request.getPath()).isEqualTo("/docs/index.html");
        assertThat(request.getQueryParameters("somekey")).isEqualTo(new String[]{});
        assertThat(request.getHttpVersion()).isEqualTo("HTTP/1.1");
        assertThat(request.getPredictedMimeType()).isEqualTo("text/html");
    }

    @Test
    void parseQueryString() {
        String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        Request request = requestParser.parse(requestLine);

        assertThat(request.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(request.getPath()).isEqualTo("/users");
        assertThat(request.getQueryParameters("userId")).isEqualTo(new String[]{"javajigi"});
        assertThat(request.getQueryParameters("password")).isEqualTo(new String[]{"password"});
        assertThat(request.getQueryParameters("name")).isEqualTo(new String[]{"JaeSung"});
        assertThat(request.getQueryParameters("somekey")).isEqualTo(new String[]{});
        assertThat(request.getHttpVersion()).isEqualTo("HTTP/1.1");
    }
}
