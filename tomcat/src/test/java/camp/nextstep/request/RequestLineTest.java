package camp.nextstep.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestLineTest {
    @Test
    void parseGetRequest() {
        RequestLine requestLine = new RequestLine("GET /docs/index.html HTTP/1.1");

        requestLine.process();

        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parsePostRequest() {
        RequestLine requestLine = new RequestLine("POST /docs/index.html HTTP/1.1");

        requestLine.process();

        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/docs/index.html");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parseQueryString() {
        RequestLine requestLine = new RequestLine("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        requestLine.process();

        assertThat(requestLine.getMethod()).isEqualTo(RequestMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }
}
