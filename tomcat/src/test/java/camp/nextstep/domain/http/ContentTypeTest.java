package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static camp.nextstep.domain.http.ContentType.TEXT_HTML;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeTest {

    @ParameterizedTest
    @CsvSource(value = {".html, TEXT_HTML", ".css, TEXT_CSS"})
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

    @ParameterizedTest
    @CsvSource(value = {"/index.html,true", "/index,false"})
    void 파일의_확장자가_있는지_확인한다(String givenPath, boolean expected) {
        boolean actual = ContentType.isSupportableExtension(givenPath);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void utf8이_포함된_content_type을_반환한다() {
        String actual = TEXT_HTML.getUtf8ContentType();
        assertThat(actual).isEqualTo("text/html;charset=utf-8");
    }
}
