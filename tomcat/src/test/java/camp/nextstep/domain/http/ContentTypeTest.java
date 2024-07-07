package camp.nextstep.domain.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"html,HTML", "css,CSS"})
    void 확장자값을_통해_ContentType을_생성한다(String givenExtension, ContentType expected) {
        ContentType actual = ContentType.fromExtension(givenExtension);
        assertThat(actual).isEqualTo(expected);
    }
}
