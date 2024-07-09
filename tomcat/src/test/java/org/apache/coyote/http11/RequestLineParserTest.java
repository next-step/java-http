package org.apache.coyote.http11;

import org.apache.coyote.http11.model.QueryParams;
import org.apache.coyote.http11.model.RequestLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestLineParserTest {

    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String TEST_PATH = "/users";
    private static final String TEST_PROTOCOL = "HTTP";
    private static final String TEST_VERSION = "1.1";
    private static final String HTML = ".html";
    private static final String BLANK = " ";
    private static final String SLASH = "/";
    private static final String TEST_PATH_QUERY_STRING = "/users?userId=javajigi&password=password&name=JaeSung";
    private final RequestLineParser requestLineParser = RequestLineParser.getInstance();

    @Test
    void httpGetParsingTest() {
        // given
        final String request = createTestRequestString(GET_METHOD, TEST_PATH);
        final RequestLine getRequestLine = requestLineParser.parse(request);

        // when
        final String httpMethod = getRequestLine.httpMethod()
                .name();
        final String path = getRequestLine.url();
        final String protocol = getRequestLine.protocol();
        final String version = getRequestLine.version();

        // then
        assertAll(
                () -> assertThat(httpMethod).isEqualTo(GET_METHOD),
                () -> assertThat(path).isEqualTo(TEST_PATH + HTML),
                () -> assertThat(protocol).isEqualTo(TEST_PROTOCOL),
                () -> assertThat(version).isEqualTo(TEST_VERSION)
        );
    }

    @Test
    void httpPostParsingTest() {
        // given
        final String request = createTestRequestString(POST_METHOD, TEST_PATH);
        final RequestLine getRequestLine = requestLineParser.parse(request);

        // when
        final String httpMethod = getRequestLine.httpMethod()
                .name();
        final String path = getRequestLine.url();
        final String protocol = getRequestLine.protocol();
        final String version = getRequestLine.version();

        // then
        assertAll(
                () -> assertThat(httpMethod).isEqualTo(POST_METHOD),
                () -> assertThat(path).isEqualTo(TEST_PATH + HTML),
                () -> assertThat(protocol).isEqualTo(TEST_PROTOCOL),
                () -> assertThat(version).isEqualTo(TEST_VERSION)
        );
    }

    @Test
    void queryStringParsingTest() {
        // given
        final String request = createTestRequestString(GET_METHOD, TEST_PATH_QUERY_STRING);
        final RequestLine requestLine = requestLineParser.parse(request);

        // when
        final QueryParams result = requestLine.queryParams();

        // then
        assertAll(
                () -> assertThat(result.valueBy("userId")).isEqualTo("javajigi"),
                () -> assertThat(result.valueBy("password")).isEqualTo("password"),
                () -> assertThat(result.valueBy("name")).isEqualTo("JaeSung")
        );
    }

    @Test
    void toLineTest() {
        // given
        final String request = createTestRequestString(GET_METHOD, TEST_PATH);
        final RequestLine requestLine = requestLineParser.parse(request);

        // when
        final String result = requestLine.toLine();

        // then
        assertThat(result).isEqualTo("GET /users.html HTTP/1.1");
    }

    @Test
    void toLineHasQueryStringTest() {
        // given
        final String request = createTestRequestString(GET_METHOD, TEST_PATH_QUERY_STRING);
        final RequestLine requestLine = requestLineParser.parse(request);

        // when
        final String result = requestLine.toLine();

        // then
        assertThat(result).isEqualTo("GET /users.html?password=password&name=JaeSung&userId=javajigi HTTP/1.1");
    }

    private String createTestRequestString(final String httpMethod, final String path) {
        return httpMethod + BLANK + path + BLANK + TEST_PROTOCOL + SLASH + TEST_VERSION;
    }
}
