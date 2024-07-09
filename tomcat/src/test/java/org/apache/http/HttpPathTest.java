package org.apache.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HttpPathTest {

    @Test
    void fromUrl_without_questionMark() {
        Assertions.assertThat(HttpPath.fromUrl("url")).isEqualTo(new HttpPath("url"));
    }

    @Test
    void fromUrl_with_questionMark() {
        Assertions.assertThat(HttpPath.fromUrl("url?hello")).isEqualTo(new HttpPath("url"));
    }

}