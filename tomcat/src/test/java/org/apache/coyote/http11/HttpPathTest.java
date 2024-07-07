package org.apache.coyote.http11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HttpPath 클래스는")
public class HttpPathTest {

    @DisplayName("HttpPath 객체를 생성할 수 있다.")
    @Test
    void create_HttpPath() {
        // given
        final String httpPath = "/users";

        // when
        final var result = HttpPath.from(httpPath);

        // then
        Assertions.assertThat(result.getPath()).isEqualTo("/users");
        Assertions.assertThat(result.getQuery().getParameters()).hasSize(0);
    }

    @DisplayName("QueryString이 있는 HttpPath 객체를 생성할 수 있다.")
    @Test
    void create_HttpPath_With_QueryString() {
        // given
        final String httpPath = "/users?userId=1";

        // when
        final var result = HttpPath.from(httpPath);

        // then
        Assertions.assertThat(result.getPath()).isEqualTo("/users");
        Assertions.assertThat(result.getQuery().getParameter("userId")).isEqualTo("1");
    }
}
