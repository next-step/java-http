package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHeadersTest {

    @Test
    void 헤더에_입력될_구분자가_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> HttpHeaders.from(List.of("Content-Type:ERROR")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Header값이 정상적으로 입력되지 않았습니다 - Content-Type:ERROR");
    }

    @Test
    void 헤더에_입력될_포멧이_잘못된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> HttpHeaders.from(List.of("Content-Type: ERROR: ERROR")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Header값이 정상적으로 입력되지 않았습니다 - Content-Type: ERROR: ERROR");
    }

    @Test
    void 헤더가_빈_객체를_생성할_수_있다() {
        HttpHeaders actual = HttpHeaders.from(emptyList());
        assertThat(actual.getHeaders()).isEmpty();
    }

    @ParameterizedTest
    @CsvSource(value = {"Content-Length: 55,false", "'',true"})
    void 빈값인지_확인한다(String givenHeaders, boolean expected) {
        boolean actual = HttpHeaders.from(List.of(givenHeaders.trim())).isEmpty();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 쿠키가_필터링된_헤더값을_파싱하여_생성한다() {
        HttpHeaders actual = HttpHeaders.from(List.of("Content-Length: 55", "Content-Type: A", "Cookie: name=jinyoung; password=1234"));
        assertThat(actual.getHeaders()).containsExactly(Map.entry("Content-Length", "55"), Map.entry("Content-Type", "A"));
    }

    @Test
    void 쿠키가_포함되지_않은_헤더값을_파싱하여_생성한다() {
        HttpHeaders actual = HttpHeaders.from(List.of("Content-Length: 55", "Content-Type: A"));
        assertThat(actual.getHeaders()).containsExactly(Map.entry("Content-Length", "55"), Map.entry("Content-Type", "A"));
    }

    @ParameterizedTest
    @CsvSource(value = {"Content-Length: 55,true", "Content-Type: A,false"})
    void content_length_헤더를_가지는지_확인할_수_있다(String givenHeaders, boolean expected) {
        boolean actual = HttpHeaders.from(List.of(givenHeaders)).containsContentLength();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void content_length가_없는데_가져오려하면_예외가_발생한다() {
        assertThatThrownBy(() -> HttpHeaders.from(List.of("Content-Type: A")).getContentLength())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Content-Length가 존재하지 않습니다.");
    }

    @Test
    void content_length를_가져온다() {
        int actual = HttpHeaders.from(List.of("Content-Length: 55")).getContentLength();
        assertThat(actual).isEqualTo(55);
    }
}
