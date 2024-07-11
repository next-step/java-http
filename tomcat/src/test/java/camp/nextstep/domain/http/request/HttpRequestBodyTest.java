package camp.nextstep.domain.http.request;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestBodyTest {

    @Test
    void 기본생성자인_경우_빈맵을_반환한다() {
        HttpRequestBody actual = new HttpRequestBody();
        assertThat(actual.getValues()).isEmpty();
    }

    @Test
    void key_value가_잘못된_형태로_입력된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> HttpRequestBody.from("name=jin=young"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("RequestBody값이 정상적으로 입력되지 않았습니다 - name=jin=young");
    }

    @Test
    void request_body의_단일_entry가_파싱된다() {
        HttpRequestBody actual = HttpRequestBody.from("name=jinyoung");
        assertThat(actual.getValues()).isEqualTo(Map.of("name", "jinyoung"));
    }

    @Test
    void rerquest_body를_파싱한다() {
        HttpRequestBody actual = HttpRequestBody.from("name=jinyoung&password=1234");
        assertThat(actual.getValues()).isEqualTo(Map.of("name", "jinyoung", "password", "1234"));
    }

    @Test
    void 존재하지않는_key의_값을_가져가려하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpRequestBody().getValue("empty"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 데이터입니다.");
    }

    @Test
    void key값을_통해_value를_가져온다() {
        String actual = new HttpRequestBody(Map.of("key", "value")).getValue("key");
        assertThat(actual).isEqualTo("value");
    }
}
