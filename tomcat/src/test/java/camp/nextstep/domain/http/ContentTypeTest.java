package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"html, TEXT_HTML", "css, TEXT_CSS"})
    void 확장자값을_통해_ContentType을_생성한다(String givenExtension, ContentType expected) {
        ContentType actual = ContentType.fromExtension(givenExtension);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 존재하지않는_확장자값을_입력한_경우_예외가_발생한다() {
        assertThatThrownBy(() -> ContentType.fromExtension("error"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 확장자입니다.");
    }
}
