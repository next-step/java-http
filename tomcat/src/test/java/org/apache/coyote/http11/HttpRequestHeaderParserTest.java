package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpRequestHeader;
import org.apache.coyote.http11.model.constant.HttpMethod;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpRequestHeaderParserTest {
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
    private static final HttpRequestHeaderParser PARSER = new HttpRequestHeaderParser();

    @Test
    void readInputStreamTest() throws IOException {
        // given // when
        final HttpRequestHeader httpRequestHeader = PARSER.parse(TEST_REQUEST_LINES);

        // then
        assertAll(
                () -> assertThat(httpRequestHeader.requestLine().httpMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(httpRequestHeader.headerValueBy("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(httpRequestHeader.headerValueBy("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(httpRequestHeader.headerValueBy("Accept")).isEqualTo("*/*"),
                () -> assertThat(httpRequestHeader.size()).isEqualTo(4)
        );
    }
}
