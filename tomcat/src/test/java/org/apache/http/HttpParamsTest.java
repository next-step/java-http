package org.apache.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class HttpParamsTest {

    @Test
    void params() {
        Assertions.assertThat(new HttpParams("test=hello")).isEqualTo(new HttpParams(Map.of("test", "hello")));
    }

    @Test
    void noParams() {
        Assertions.assertThat(new HttpParams("teshello")).isEqualTo(new HttpParams(Map.of()));
    }

}