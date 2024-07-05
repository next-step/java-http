package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QueryStringTest {

    @Test
    void key_value가_잘못된_형태로_입력된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new QueryString("name=jin=young"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("QueryString값이 정상적으로 입력되지 않았습니다 - name=jin=young");
    }
}