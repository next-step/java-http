package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderParserTest {

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

        final HttpHeaders actual = HttpHeaderParser.parse(request);

        assertThat(actual.get("Host")).isEqualTo("localhost:8080");
        assertThat(actual.get("Accept")).isEqualTo("text/html,charset=utf-8");
        assertThat(actual.get("Connection")).isEqualTo("keep-alive");
    }

}