package org.apache.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HttpMethodTest {

    @Test
    void get() {
        Assertions.assertThat(HttpMethod.valueOf("GET")).isEqualTo(HttpMethod.GET);
    }

    @Test
    void post() {
        Assertions.assertThat(HttpMethod.valueOf("POST")).isEqualTo(HttpMethod.POST);
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> HttpMethod.valueOf("WRONG")).isInstanceOf(IllegalArgumentException.class);
    }

}