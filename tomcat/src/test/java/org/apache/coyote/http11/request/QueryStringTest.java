package org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.QueryString;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("QueryString 클래스는")
class QueryStringTest {

    @DisplayName("QueryString 객체를 생성할 수 있다.")
    @Test
    void create_QueryString() {
        // given & when
        final var result = QueryString.from("userId=1");

        // then
        assertThat(result.getParameters()).hasSize(1);
        assertThat(result.getParameter("userId")).isEqualTo("1");
    }

    @DisplayName("QueryString이 없는 QueryString 객체를 생성할 수 있다.")
    @Test
    void create_QueryString_Without_QueryString() {
        // given & when
        final var result = QueryString.from("");

        // then
        assertThat(result.getParameters()).isEmpty();
    }
}
