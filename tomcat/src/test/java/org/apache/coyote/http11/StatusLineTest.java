package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class StatusLineTest {
    @DisplayName("HTTP 응답 형식에 맞는 응답 메세지를 생성한다.")
    @Test
    void createResponseMessage() {
        StatusLine statusLine = StatusLine.of(HttpProtocol.from("HTTP/1.1"), HttpStatus.OK);

        String responseMessage = statusLine.createResponseMessage();

        assertThat(responseMessage).isEqualTo("HTTP/1.1 200 OK");
    }
}