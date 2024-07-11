package camp.nextstep.domain.http.request;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QueryStringTest {

    @Test
    void 기본_생성자는_빈_쿼리_파라미터를_가진다() {
        QueryString actual = new QueryString();
        assertThat(actual.getQueryParameters()).isEmpty();
    }

    @Test
    void key_value가_잘못된_형태로_입력된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> QueryString.from("name=jin=young"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("QueryParameter값이 정상적으로 입력되지 않았습니다 - name=jin=young");
    }

    @Test
    void query_string의_단일_query_parameter가_파싱된다() {
        QueryString actual = QueryString.from("name=jinyoung");
        assertThat(actual.getQueryParameters()).isEqualTo(Map.of("name", "jinyoung"));
    }

    @Test
    void 여러_query_parameter를_파싱한다() {
        QueryString actual = QueryString.from("name=jinyoung&password=1234");
        assertThat(actual.getQueryParameters()).isEqualTo(Map.of("name", "jinyoung", "password", "1234"));
    }
}
