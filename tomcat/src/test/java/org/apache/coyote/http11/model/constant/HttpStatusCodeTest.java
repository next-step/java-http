package org.apache.coyote.http11.model.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusCodeTest {

    @DisplayName("HttpStatusCode의 responseMessage() 메서드 테스트")
    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "OK, HTTP/1.1 200 OK",
                    "CREATED, HTTP/1.1 201 CREATED",
                    "FOUND, HTTP/1.1 302 FOUND",
                    "BAD_REQUEST, HTTP/1.1 400 BAD REQUEST",
                    "UNAUTHORIZED, HTTP/1.1 401 UNAUTHORIZED",
                    "NOT_FOUND, HTTP/1.1 404 NOT FOUND",
                    "INTERNAL_SERVER_ERROR, HTTP/1.1 500 INTERNAL SERVER ERROR"
            }
    )
    void responseMessageTest(final HttpStatusCode httpStatusCode, final String expected) {
        // given // when
        final String result = httpStatusCode.responseMessage();

        // then
        assertThat(result).isEqualTo(expected + " ");
    }
}
