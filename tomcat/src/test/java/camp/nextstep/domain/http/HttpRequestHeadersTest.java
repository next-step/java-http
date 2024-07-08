package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestHeadersTest {

    @Test
    void 헤더에_입력될_구분자가_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpRequestHeaders(List.of("Content-Type:ERROR")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("RequestHeader값이 정상적으로 입력되지 않았습니다 - Content-Type:ERROR");
    }

    @Test
    void 헤더에_입력될_포멧이_잘못된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpRequestHeaders(List.of("Content-Type: ERROR: ERROR")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("RequestHeader값이 정상적으로 입력되지 않았습니다 - Content-Type: ERROR: ERROR");
    }
}
