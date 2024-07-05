package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpMethodTest {

    @Test
    void 존재하지_않는_Http_Method를_입력하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> HttpMethod.from("ERROR"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 HttpMethod로 요청되었습니다. - ERROR");
    }

    @Test
    void Http_Method로_파싱한다() {
        HttpMethod actual = HttpMethod.from("GET");
        assertThat(actual).isEqualTo(HttpMethod.GET);
    }
}