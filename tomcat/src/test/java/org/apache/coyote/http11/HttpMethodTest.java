package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpMethodTest {

    @Nested
    class From {

        @ParameterizedTest
        @ValueSource(strings = {"AAA", "1234", "GOT"})
        @DisplayName("유효하지 않은 HttpMethod 일 경우 에러가 발생한다")
        void fail(String input) {
            assertThrows(IllegalArgumentException.class,
                    () -> HttpMethod.from(input));
        }

        @ParameterizedTest
        @ValueSource(strings = {"GET", "POST"})
        @DisplayName("유효한 HttpMethod 객체 변환에 성공한다")
        void success(String input) {
            assertThatCode(() -> HttpMethod.from(input)).doesNotThrowAnyException();
        }
    }
}