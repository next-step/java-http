package org.apache.coyote.http11.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderTest {

    @DisplayName("requestHeader에 Content-Length로 RequestBody가 존재하는지 확인한다.")
    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "content-length, 80, true",
                    "content-length, 0, false",
                    "content-length, 2, true",
                    "test, test, false",
            }
    )
    void hasRequestBodyTest(final String key, final String value, final boolean expected) {
        // given
        final HttpRequestHeader httpRequestHeader = new HttpRequestHeader(Map.of(key, value));

        // when
        final boolean result = httpRequestHeader.hasRequestBody();

        // then
        assertThat(result).isEqualTo(expected);
    }
}
