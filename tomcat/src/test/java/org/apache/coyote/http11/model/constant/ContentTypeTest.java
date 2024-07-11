package org.apache.coyote.http11.model.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeTest {

    @DisplayName("ContentType에 존재하지 않는 extension으로 탐색하면 에러를 던진다.")
    @Test
    void findByExtensionThrowTest() {
        // given
        final String extension = ".txt";

        // when // then
        assertThatThrownBy(() -> {
            ContentType.findByExtension(extension);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("ContentType에 존재하는 extension으로 탐색하면 ContentType을 반환한다.")
    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    ".js, APPLICATION_JAVASCRIPT",
                    ".html, TEXT_HTML",
                    ".css, TEXT_CSS"
            }
    )
    void findByExtensionTest(final String extension, final ContentType expect) {
        // given // when
        final ContentType result = ContentType.findByExtension(extension);

        // then
        assertThat(result).isEqualTo(expect);
    }

    @DisplayName("ContentType에 존재하는 정적 타입을 path로 탐색한다.")
    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "swe.js, true",
                    "wewe.html, false",
                    "wewe.css, true"
            }
    )
    void validateStaticTypeTest(final String pathString, final boolean expect) {
        // given // when
        final boolean result = ContentType.isStaticType(pathString);

        // then
        assertThat(result).isEqualTo(expect);
    }
}
