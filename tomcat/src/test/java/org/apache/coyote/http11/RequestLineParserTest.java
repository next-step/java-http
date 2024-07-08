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
    void from_get() {
        final String requestLine = "GET /users HTTP/1.1";

        final HttpServletRequest expected = parser.parse(requestLine);

        assertThat(expected.httpMethod()).isEqualTo(HttpMethod.GET);
        assertThat(expected.httpPath().path()).isEqualTo(TEST_HTTP_PATH);
        assertThat(expected.protocol().value()).isEqualTo(TEST_HTTP_PROTOCOL);
        assertThat(expected.protocolVersion().value()).isEqualTo(TEST_HTTPS_PROTOCOL_VERSION);
    }

    @Test
    @DisplayName("POST 요청을 HttpMethod, HttpPath, HttpProtocol, protocolVersion 으로 파싱할 수 있다.")
    void from_post() {
        final String requestLine = "POST /users HTTP/1.1";

        final HttpServletRequest expected = parser.parse(requestLine);

        assertThat(expected.httpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(expected.httpPath().path()).isEqualTo(TEST_HTTP_PATH);
        assertThat(expected.protocol().value()).isEqualTo(TEST_HTTP_PROTOCOL);
        assertThat(expected.protocolVersion().value()).isEqualTo(TEST_HTTPS_PROTOCOL_VERSION);
    }

    @Test
    @DisplayName("queryString 이 포함된 GET 요청을 HttpMethod, HttpPath, HttpProtocol, protocolVersion 으로 파싱할 수 있다.")
    void from_getWithQueryString() {
        final String requestLine = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        final HttpServletRequest expected = parser.parse(requestLine);

        assertThat(expected.httpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(expected.httpPath().path()).isEqualTo(TEST_HTTP_PATH);
        assertThat(expected.httpPath().queryParams().value().get(QUERY_PARAMS_KEY_1)).isEqualTo(QUERY_PARAMS_VALUE_1);
        assertThat(expected.httpPath().queryParams().value().get(QUERY_PARAMS_KEY_2)).isEqualTo(QUERY_PARAMS_VALUE_2);
        assertThat(expected.httpPath().queryParams().value().get(QUERY_PARAMS_KEY_3)).isEqualTo(QUERY_PARAMS_VALUE_3);
        assertThat(expected.protocol().value()).isEqualTo(TEST_HTTP_PROTOCOL);
        assertThat(expected.protocolVersion().value()).isEqualTo(TEST_HTTPS_PROTOCOL_VERSION);
    }

}