package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.constant.HttpMethod;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpHeadersParserTest {
    private static final String TEST_REQUEST_LINE = "GET /index.html HTTP/1.1 ";
    private static final String TEST_HOST = "Host: localhost:8080 ";
    private static final String TEST_CONNECTION = "Connection: keep-alive ";
    private static final String TEST_ACCEPT = "Accept: */* ";
    private static final String TEST_NOT_HEADER_LINE = "is not header line";
    private static final List<String> TEST_REQUEST_LINES = List.of(
            TEST_REQUEST_LINE,
            TEST_HOST,
            TEST_CONNECTION,
            TEST_ACCEPT,
            TEST_NOT_HEADER_LINE,
            "",
            "");
    private final HttpRequestHeaderParser parser = HttpRequestHeaderParser.getInstance();

    @Test
    void readInputStreamTest() throws IOException {
        // given // when
        final HttpHeaders httpHeaders = parser.parse(TEST_REQUEST_LINES);

        // then
        assertAll(
                () -> assertThat(httpHeaders.requestLine().httpMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(httpHeaders.headerValueBy("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpHeaders.headerValueBy("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(httpHeaders.headerValueBy("Accept")).isEqualTo("*/*"),
                () -> assertThat(httpHeaders.size()).isEqualTo(4)
        );
    }
}
