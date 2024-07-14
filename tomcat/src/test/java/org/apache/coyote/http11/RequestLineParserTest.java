package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineParserTest {

    private final RequestLineParser parser = new RequestLineParser();

    private static final String TEST_HTTP_PATH = "/users";
    private static final String TEST_HTTP_PROTOCOL = "HTTP";
    private static final String TEST_HTTPS_PROTOCOL_VERSION = "1.1";
    private static final String QUERY_PARAMS_KEY_1 = "userId";
    private static final String QUERY_PARAMS_KEY_2 = "password";
    private static final String QUERY_PARAMS_KEY_3 = "name";
    private static final String QUERY_PARAMS_VALUE_1 = "javajigi";
    private static final String QUERY_PARAMS_VALUE_2 = "password";
    private static final String QUERY_PARAMS_VALUE_3 = "JaeSung";

    @Test
    @DisplayName("GET 요청을 HttpMethod, HttpPath, HttpProtocol, protocolVersion 으로 파싱할 수 있다.")
    void parse_get() {
        final String requestLine = "GET /users HTTP/1.1";

        final HttpRequest expected = parser.parse(requestLine);

        assertThat(expected.getHttpPath()).isEqualTo(HttpMethod.GET);
        assertThat(expected.getRequestTarget().path()).isEqualTo(TEST_HTTP_PATH);
        assertThat(expected.getRequestTarget().protocol().value()).isEqualTo(TEST_HTTP_PROTOCOL);
        assertThat(expected.getRequestTarget().protocolVersion().value()).isEqualTo(TEST_HTTPS_PROTOCOL_VERSION);
    }

    @Test
    @DisplayName("POST 요청을 HttpMethod, HttpPath, HttpProtocol, protocolVersion 으로 파싱할 수 있다.")
    void parse_post() {
        final String requestLine = "POST /users HTTP/1.1";

        final HttpRequest expected = parser.parse(requestLine);

        assertThat(expected.getHttpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(expected.getRequestTarget().path()).isEqualTo(TEST_HTTP_PATH);
        assertThat(expected.getRequestTarget().protocol().value()).isEqualTo(TEST_HTTP_PROTOCOL);
        assertThat(expected.getRequestTarget().protocolVersion().value()).isEqualTo(TEST_HTTPS_PROTOCOL_VERSION);
    }

    @Test
    @DisplayName("queryString 이 포함된 GET 요청을 HttpMethod, HttpPath, HttpProtocol, protocolVersion 으로 파싱할 수 있다.")
    void parse_getWithQueryString() {
        final String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        final HttpRequest expected = parser.parse(requestLine);

        assertThat(expected.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(expected.getRequestTarget().path()).isEqualTo(TEST_HTTP_PATH);
        assertThat(expected.getRequestTarget().queryParamsMap().value().get(QUERY_PARAMS_KEY_1)).isEqualTo(QUERY_PARAMS_VALUE_1);
        assertThat(expected.getRequestTarget().queryParamsMap().value().get(QUERY_PARAMS_KEY_2)).isEqualTo(QUERY_PARAMS_VALUE_2);
        assertThat(expected.getRequestTarget().queryParamsMap().value().get(QUERY_PARAMS_KEY_3)).isEqualTo(QUERY_PARAMS_VALUE_3);
        assertThat(expected.getRequestTarget().protocol().value()).isEqualTo(TEST_HTTP_PROTOCOL);
        assertThat(expected.getRequestTarget().protocolVersion().value()).isEqualTo(TEST_HTTPS_PROTOCOL_VERSION);
    }

    @Test
    @DisplayName("헤더의 키, 값 들을 파싱한다")
    void parseHeaders() {
        final String request = String.join("\r\n",
                "GET /register HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Accept: text/html,charset=utf-8 ",
                "Connection: keep-alive ",
                "",
                "");

        final HttpRequest actual = parser.parse(request);

        assertThat(actual.getHttpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(actual.getHeaders().get("Host")).isEqualTo("localhost:8080");
        assertThat(actual.getHeaders().get("Accept")).isEqualTo("text/html,charset=utf-8");
        assertThat(actual.getHeaders().get("Connection")).isEqualTo("keep-alive");
    }

}
