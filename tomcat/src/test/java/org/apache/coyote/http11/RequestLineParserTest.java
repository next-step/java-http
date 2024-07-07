package org.apache.coyote.http11;

import java.util.Map;
import java.util.Objects;
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
        Assertions.assertThat(result.getMethod()).isEqualTo(expected.getMethod());
        Assertions.assertThat(result.getPath()).isEqualTo(expected.getPath());
        Assertions.assertThat(result.getProtocol()).isEqualTo(expected.getProtocol());
        Assertions.assertThat(result.getVersion()).isEqualTo(expected.getVersion());
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
        Assertions.assertThat(result.getMethod()).isEqualTo(expected.getMethod());
        Assertions.assertThat(result.getPath()).isEqualTo(expected.getPath());
        Assertions.assertThat(result.getProtocol()).isEqualTo(expected.getProtocol());
        Assertions.assertThat(result.getVersion()).isEqualTo(expected.getVersion());
    }

    @Test
    @DisplayName("QueryString을 파싱할 수 있다.")
    void parse_QueryString() {
        // given
        final String httpRequest = String.join("\r\n",
                                               "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ",
                                               "");
        final var socket = new StubSocket(httpRequest);
        final var parser = new RequestLineParser(socket);

        // when
        final var result = parser.parse();
        // then
        Map<String, Object> parameters = result.getParameters();
        Assertions.assertThat(parameters.get("userId")).isEqualTo("javajigi");
        Assertions.assertThat(parameters.get("password")).isEqualTo("password");
        Assertions.assertThat(parameters.get("name")).isEqualTo("JaeSung");
    }
}
