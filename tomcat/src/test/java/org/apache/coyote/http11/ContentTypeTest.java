package org.apache.coyote.http11;

import org.apache.coyote.http11.model.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"AAA", "1234", "GOT"})
    @DisplayName("유효하지 않은 확장자일 경우 ContentType 변환에 실패한다")
    void fail(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> ContentType.fromExtension(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"html", "css"})
    @DisplayName("유효한 ContentType 객체 변환에 성공한다")
    void success(String input) {
        assertThatCode(() -> ContentType.fromExtension(input)).doesNotThrowAnyException();
    }
}