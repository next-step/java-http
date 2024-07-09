package org.apache.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HttpProtocolTest {

    @Test
    void from_protocol() {
        Assertions.assertThat(new HttpProtocol("HTTP/1.1")).isEqualTo(HttpProtocol.HTTP_11);
    }

    // protocol의 wrong 테스트는 진행하지 않는다.
}