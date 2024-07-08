package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestBodyTest {

    @Test
    void key_value가_잘못된_형태로_입력된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpRequestBody("name=jin=young"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("RequestBody값이 정상적으로 입력되지 않았습니다 - name=jin=young");
    }

    @Test
    void request_body의_단일_entry가_파싱된다() {
        HttpRequestBody actual = new HttpRequestBody("name=jinyoung");
        assertThat(actual.getValues()).isEqualTo(Map.of("name", "jinyoung"));
    }

    @Test
    void rerquest_body를_파싱한다() {
        HttpRequestBody actual = new HttpRequestBody("name=jinyoung&password=1234");
        assertThat(actual.getValues()).isEqualTo(Map.of("name", "jinyoung", "password", "1234"));
    }
}
