package org.apache.coyote.http11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HttpPath 클래스는")
class HttpPathTest {

    @DisplayName("HttpPath 객체를 생성할 수 있다.")
    @Test
    void create_HttpPath() {
        // given
        final String httpPath = "/users";

        // when
        final var result = HttpPath.from(httpPath);

        // then
        Assertions.assertThat(result.getPath()).isEqualTo("/users");
        Assertions.assertThat(result.getQuery().getParameters()).isEmpty();
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

    @DisplayName("확장자를 반환할 수 있다.")
    @Test
    void getExtension() {
        // given
        final String httpPath = "/users.html";

        // when
        final var result = HttpPath.from(httpPath);

        // then
        Assertions.assertThat(result.getExtension()).isEqualTo(".html");
    }

    @DisplayName("확장자가 없는 경우 빈 문자열을 반환한다.")
    @Test
    void getExtension_Without_Extension() {
        // given
        final String httpPath = "/users";

        // when
        final var result = HttpPath.from(httpPath);

        // then
        Assertions.assertThat(result.getExtension()).isEmpty();
    }

    @DisplayName("루트 경로인지 확인할 수 있다.")
    @Test
    void isRootPath() {
        // given
        final String httpPath = "/";

        // when
        final var result = HttpPath.from(httpPath);

        // then
        Assertions.assertThat(result.isRootPath()).isTrue();
    }
}
