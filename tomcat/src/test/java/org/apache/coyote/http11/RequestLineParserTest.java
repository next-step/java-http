package org.apache.coyote.http11;

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
    private static final String BLANK = " ";
    private static final String SLASH = "/";
    private static final String TEST_GET_REQUEST_STRING = GET_METHOD + BLANK + TEST_PATH + BLANK + TEST_PROTOCOL + SLASH + TEST_VERSION;
    private static final String TEST_POST_REQUEST_STRING = POST_METHOD + BLANK + TEST_PATH + BLANK + TEST_PROTOCOL + SLASH + TEST_VERSION;
    private static final RequestLineParser REQUEST_LINE_PARSER = new RequestLineParser();

    @Test
    void httpGetParsingTest() {
        // given
        final RequestLine getRequestLine = REQUEST_LINE_PARSER.parse(TEST_GET_REQUEST_STRING);

        // when
        final String httpMethod = getRequestLine.httpMethod()
                .name();
        final String path = getRequestLine.url();
        final String protocol = getRequestLine.protocol();
        final String version = getRequestLine.version();

        // then
        assertAll(
                () -> assertThat(httpMethod).isEqualTo(GET_METHOD),
                () -> assertThat(path).isEqualTo(TEST_PATH),
                () -> assertThat(protocol).isEqualTo(TEST_PROTOCOL),
                () -> assertThat(version).isEqualTo(TEST_VERSION)
        );
    }

    @Test
    void httpPostParsingTest() {
        // given
        final RequestLine getRequestLine = REQUEST_LINE_PARSER.parse(TEST_POST_REQUEST_STRING);

        // when
        final String httpMethod = getRequestLine.httpMethod()
                .name();
        final String path = getRequestLine.url();
        final String protocol = getRequestLine.protocol();
        final String version = getRequestLine.version();

        // then
        assertAll(
                () -> assertThat(httpMethod).isEqualTo(POST_METHOD),
                () -> assertThat(path).isEqualTo(TEST_PATH),
                () -> assertThat(protocol).isEqualTo(TEST_PROTOCOL),
                () -> assertThat(version).isEqualTo(TEST_VERSION)
        );
    }
}
