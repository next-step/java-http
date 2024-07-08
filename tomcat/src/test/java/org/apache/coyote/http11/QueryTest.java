package org.apache.coyote.http11;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Query 클래스는")
class QueryTest {

    @DisplayName("Query 객체를 생성할 수 있다.")
    @Test
    void create_Query() {
        // given
        final String queryString = "userId=1";

        // when
        final var result = Query.from(queryString);

        // then
        assertThat(result.getParameters()).hasSize(1);
        assertThat(result.getParameter("userId")).isEqualTo("1");
    }

    @DisplayName("QueryString이 없는 Query 객체를 생성할 수 있다.")
    @Test
    void create_Query_Without_QueryString() {
        // given
        final String queryString = "";

        // when
        final var result = Query.from(queryString);

        // then
        assertThat(result.getParameters()).isEmpty();
    }
}
