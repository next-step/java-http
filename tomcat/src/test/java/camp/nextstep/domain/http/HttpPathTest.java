package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpPathTest {

    @Test
    void query_string이_없는_경우_path만_파싱한다() {
        HttpPath actual = new HttpPath("/docs/index.html");
        assertThat(actual.getPath()).isEqualTo("/docs/index.html");
    }

    @Test
    void http_path의_포맷이_잘못된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpPath("/docs/index.html?name=value?name=value"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("HttpPath값이 정상적으로 입력되지 않았습니다 - /docs/index.html?name=value?name=value");
    }

    @Test
    void query_string_구분자가_있는_경우_쿼리스트링을_파싱한다() {
        HttpPath actual = new HttpPath("/docs/index.html?name=jinyoung");
        assertThat(actual.getQueryString()).isEqualTo(Map.of("name", "jinyoung"));
    }
}