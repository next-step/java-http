package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidContentTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "text/html;charset=UTF-8:HTML",
            "text/css;charset=UTF-8:CSS",
            "text/javascript;charset=UTF-8:JAVASCRIPT"
    }, delimiter = ':')
    void ContentType_을_생성_할_수_있다(final String input, final ContentType expected) {
        final ContentType contentType = ContentType.from(input);

        assertThat(contentType).isEqualTo(expected);
    }

    @Test
    void 존재하지_않는_ContentType_을_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> ContentType.from("notExist"))
                .isInstanceOf(InvalidContentTypeException.class);
    }


    @ParameterizedTest
    @CsvSource(value = {
            "/index.html:HTML",
            "/style.css:CSS",
            "/scripts.js:JAVASCRIPT"
    }, delimiter = ':')
    void ContentType_을_HttpPath_로_생성_할_수_있다(final String path, final ContentType expected) {
        final ContentType contentType = ContentType.from(new HttpPath(path));

        assertThat(contentType).isEqualTo(expected);
    }
}
