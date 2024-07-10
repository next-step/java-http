package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusTest {
    @DisplayName("HTTP 응답 형식에 맞는 응답 메세지를 생성한다.")
    @Test
    void createResponseMessage() {
        String responseMessage = HttpStatus.OK.createResponseMessage();

        assertThat(responseMessage).isEqualTo("200 OK");
    }
}