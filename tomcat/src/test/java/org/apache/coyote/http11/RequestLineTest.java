package org.apache.coyote.http11;

import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RequestParser class는")
class RequestLineTest {

    @DisplayName("GET 메서드를 통해 요청을 받았을 때 RequestLine 객체를 반환한다")
    @Test
    void parse_GET_RequestLine() {
        // given
        final String httpRequest = String.join("\r\n",
                                               "GET /users HTTP/1.1 ",
                                               "");
        // when
        final var result = RequestLine.from(httpRequest);

        // then
        Assertions.assertThat(result.getMethod()).isEqualTo("GET");
        Assertions.assertThat(result.getPath().getPath()).isEqualTo("/users");
        Assertions.assertThat(result.getProtocol()).isEqualTo("HTTP");
        Assertions.assertThat(result.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("POST 메서드를 통해 요청을 받았을 때 RequestLine 객체를 반환한다")
    @Test
    void parse_POST_RequestLine() {
        // given
        final String httpRequest = String.join("\r\n",
                                               "POST /users HTTP/1.1 ",
                                               "");
        // when
        final var result = RequestLine.from(httpRequest);

        // then
        Assertions.assertThat(result.getMethod()).isEqualTo("POST");
        Assertions.assertThat(result.getPath().getPath()).isEqualTo("/users");
        Assertions.assertThat(result.getProtocol()).isEqualTo("HTTP");
        Assertions.assertThat(result.getVersion()).isEqualTo("1.1");
    }

    @Test
    @DisplayName("QueryString을 파싱할 수 있다.")
    void parse_QueryString() {
        // given
        final String httpRequest = String.join("\r\n",
                                               "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ",
                                               "");

        // when
        final var result = RequestLine.from(httpRequest);

        // then
        Map<String, Object> parameters = result.getParameters();
        Assertions.assertThat(parameters.get("userId")).isEqualTo("javajigi");
        Assertions.assertThat(parameters.get("password")).isEqualTo("password");
        Assertions.assertThat(parameters.get("name")).isEqualTo("JaeSung");
    }
}

