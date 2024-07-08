package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPathTest {

    @Test
    @DisplayName("HttpPath 객체를 생성한다")
    void from_notExistQueryString() {
        String requestPath = "/users";

        HttpPath actual = HttpPath.from(requestPath);

        assertThat(actual.path()).isEqualTo("/users");
        assertThat(actual.queryParams()).isNull();
    }

    @Test
    @DisplayName("path 와 queryParams 를 올바르게 파싱하여 HttpPath 객체를 생성한다")
    void from_existQueryString() {
        String requestPath = "/users?userId=javajigi&password=password&name=JaeSung";

        HttpPath actual = HttpPath.from(requestPath);

        assertThat(actual.path()).isEqualTo("/users");
        assertThat(actual.queryParams()).isNotNull();
    }

}