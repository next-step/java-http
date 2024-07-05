package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class HttpMethodTest {

    @Test
    void 존재하지_않는_Http_Method를_입력하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> HttpMethod.from("ERROR"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 HttpMethod로 요청되었습니다. - ERROR");
    }
}