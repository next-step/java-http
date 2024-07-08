package org.apache.coyote.http11;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HttpPath 클래스는")
class HttpPathTest {

    @DisplayName("HttpPath 객체를 생성할 수 있다.")
    @Test
    void create_HttpPath() {
        // given & when
        final var result = HttpPath.from("/users");

        // then
        assertThat(result.getPath()).isEqualTo("/users");
        assertThat(result.getQuery().getParameters()).isEmpty();
    }

    @DisplayName("QueryString이 있는 HttpPath 객체를 생성할 수 있다.")
    @Test
    void create_HttpPath_With_QueryString() {
        // given & when
        final var result = HttpPath.from("/users?userId=1");

        // then
        assertThat(result.getPath()).isEqualTo("/users");
        assertThat(result.getQuery().getParameter("userId")).isEqualTo("1");
    }

    @DisplayName("확장자를 반환할 수 있다.")
    @Test
    void getExtension() {
        // given & when
        final var result = HttpPath.from("/users.html");

        // then
        assertThat(result.getExtension()).isEqualTo(".html");
    }

    @DisplayName("확장자가 없는 경우 빈 문자열을 반환한다.")
    @Test
    void getExtension_Without_Extension() {
        // given & when
        final var result = HttpPath.from("/users");

        // then
        assertThat(result.getExtension()).isEmpty();
    }

    @DisplayName("루트 경로인지 확인할 수 있다.")
    @Test
    void isRootPath() {
        // given & when
        final var result = HttpPath.from("/");

        // then
        assertThat(result.isRootPath()).isTrue();
    }
}
