package camp.nextstep.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestTest {
    @Test
    void parseGetRequest() {
        Request request = new Request("GET /docs/index.html HTTP/1.1");

        assertThat(request.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(request.getPath()).isEqualTo("/docs/index.html");
        assertThat(request.getProtocol()).isEqualTo("HTTP");
        assertThat(request.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parsePostRequest() {
        Request request = new Request("POST /docs/index.html HTTP/1.1");

        assertThat(request.getMethod()).isEqualTo(RequestMethod.POST);
        assertThat(request.getPath()).isEqualTo("/docs/index.html");
        assertThat(request.getProtocol()).isEqualTo("HTTP");
        assertThat(request.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parseQueryString() {
        Request request = new Request("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertThat(request.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(request.getPath()).isEqualTo("/users");
        assertThat(request.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
        assertThat(request.getProtocol()).isEqualTo("HTTP");
        assertThat(request.getVersion()).isEqualTo("1.1");
    }
}
