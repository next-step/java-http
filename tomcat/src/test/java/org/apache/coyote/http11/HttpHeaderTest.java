package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderTest {
    @DisplayName("key: value 형식의 값을 HttpHeader로 파싱한다.")
    @ParameterizedTest(name = "input = {0}")
    @CsvSource({
            "Content-Type: text/html, Content-Type, text/html",
            "Host: http://localhost:8080, Host, http://localhost:8080",
    })
    void from(String keyValue, String name, String value) {
        HttpHeader httpHeader = HttpHeader.from(keyValue);

        assertThat(httpHeader).extracting("name").isEqualTo(name);
        assertThat(httpHeader).extracting("value").isEqualTo(value);
    }
}