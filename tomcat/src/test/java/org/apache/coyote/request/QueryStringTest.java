package org.apache.coyote.request;

import org.apache.exception.BadQueryStringException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

class QueryStringTest {

    @Test
    @DisplayName("QueryString을 받으면 Map으로 파싱한다.")
    void parseToQueryString() {
        // given
        String queries = "userId=arthur&password=password&name=Hyuntae";

        // when
        QueryString queryString = QueryString.parse(queries);

        // then
        assertAll(
                () -> Assertions.assertThat(queryString.getQueryParams()).containsEntry("userId", "arthur"),
                () -> Assertions.assertThat(queryString.getQueryParams()).containsEntry("password", "password"),
                () -> Assertions.assertThat(queryString.getQueryParams()).containsEntry("name", "Hyuntae")
        );
    }

    @Test
    @DisplayName("잘못된 QueryString을 받으면 예외를 던진다.")
    void fail_parseToQueryString() {
        // given
        String queries = "userId=arthur&password=password&name";

        // when & then
        Assertions.assertThatThrownBy(() -> QueryString.parse(queries))
                .isInstanceOf(BadQueryStringException.class);
    }
}
