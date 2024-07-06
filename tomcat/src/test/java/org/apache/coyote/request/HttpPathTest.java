package org.apache.coyote.request;

import org.apache.exception.BadPathException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpPathTest {

    @Test
    @DisplayName("HTTP Path만 받았을 때 HttpPath를 파싱할 수 있다.")
    void parseHttpWithPathOnly() {
        // given
        String path = "/users";

        // when
        HttpPath httpPath = HttpPath.parse(path);

        // then
        assertEquals("/users", httpPath.getPath());
    }

    @Test
    @DisplayName("HTTP Path와 QueryString을 받았을 때 HttpPath를 파싱할 수 있다.")
    void parseHttpWithPathAndQueryString() {
        // given
        String path = "/users?userId=arthur&password=password&name=Hyuntae";

        // when
        HttpPath httpPath = HttpPath.parse(path);

        // then
        assertAll(
                () -> assertThat(httpPath.getPath()).isEqualTo("/users"),
                () -> assertThat(httpPath.getQueryString().getQueryParams()).containsEntry("userId", "arthur"),
                () -> assertThat(httpPath.getQueryString().getQueryParams()).containsEntry("password", "password"),
                () -> assertThat(httpPath.getQueryString().getQueryParams()).containsEntry("name", "Hyuntae")
        );
    }

    @Test
    @DisplayName("잘못된 Path를 받으면 예외를 던진다.")
    void fail_parseHttpPath() {
        // given
        String path = "/users?userId=arthur?password=password&name";

        // when & then
        assertThatThrownBy(() -> HttpPath.parse(path))
                .isInstanceOf(BadPathException.class);
    }
}
