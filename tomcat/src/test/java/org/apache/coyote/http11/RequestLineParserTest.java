package org.apache.coyote.http11;

import org.apache.coyote.http11.RequestLine;
import org.apache.coyote.http11.RequestLineParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.StubSocket;

@DisplayName("RequestLineParser class는")
class RequestLineParserTest {

    @DisplayName("GET 메서드를 통해 요청을 받았을 때 RequestLine 객체를 반환한다")
    @Test
    void parse_GET_RequestLine() {
        // given
        final String httpRequest = String.join("\r\n",
                                               "GET /users HTTP/1.1 ",
                                               "");
        final var socket = new StubSocket(httpRequest);
        final var parser = new RequestLineParser(socket);

        // when
        final var result = parser.parse();
        final var expected = RequestLine.from(httpRequest);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @DisplayName("POST 메서드를 통해 요청을 받았을 때 RequestLine 객체를 반환한다")
    @Test
    void parse_POST_RequestLine() {
        // given
        final String httpRequest = String.join("\r\n",
                                               "POST /users HTTP/1.1 ",
                                               "");
        final var socket = new StubSocket(httpRequest);
        final var parser = new RequestLineParser(socket);

        // when
        final var result = parser.parse();
        final var expected = RequestLine.from(httpRequest);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }
}
