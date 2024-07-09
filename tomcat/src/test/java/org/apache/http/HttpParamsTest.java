package org.apache.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class HttpParamsTest {

    @Test
    void fromUrl_without_questionMark() {
        Assertions.assertThat(new HttpParams("url")).isEqualTo(new HttpParams());
    }

    @Test
    void fromUrl_with_questionMark() {
        Assertions.assertThat(new HttpParams("url?test=hello")).isEqualTo(new HttpParams(Map.of("test", "hello")));
    }

    @Test
    void fromUrl_with_wrongQueryString() {
        Assertions.assertThat(new HttpParams("url?teshello")).isEqualTo(new HttpParams());
    }

}