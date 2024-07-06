package org.apache.coyote.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestLineTest {

    @Test
    @DisplayName("HTTP GET 요청에 대한 RequestLine을 파싱할 수 있다.")
    void parseHttpGetRequest() {
        // given
        final String request = "GET /users HTTP/1.1";

        // when
        final RequestLine requestLine = RequestLine.parse(request);

        // then
        assertAll(
                () -> assertThat(requestLine.getHttpMethod()).isEqualTo("GET"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getHttpProtocol()).isEqualTo("HTTP/1.1")
        );
    }
}
