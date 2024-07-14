package org.apache.coyote.http11;

import org.apache.coyote.http11.model.RequestTarget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTargetTest {

    @Test
    @DisplayName("HttpPath 객체를 생성한다")
    void from_notExistQueryString() {
        String requestPath = "/users";
        String protocol = "HTTP/1.1";

        RequestTarget actual = RequestTarget.from(requestPath, protocol);

        assertThat(actual.path()).isEqualTo("/users");
        assertThat(actual.queryParamsMap()).isNull();
    }

    @Test
    @DisplayName("path 와 queryParams 를 올바르게 파싱하여 HttpPath 객체를 생성한다")
    void from_existQueryString() {
        String requestPath = "/users?userId=javajigi&password=password&name=JaeSung";
        String protocol = "HTTP/1.1";

        RequestTarget actual = RequestTarget.from(requestPath, protocol);

        assertThat(actual.path()).isEqualTo("/users");
        assertThat(actual.queryParamsMap()).isNotNull();
    }

}
