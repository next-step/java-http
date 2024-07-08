package org.apache.coyote.http11.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPathTest {

    @DisplayName("path가 root path인지 확인한다.")
    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "/host, false",
                    "/, true",
                    "/calls/call, false",
            }
    )
    void isRootPathTest(final String pathString, final boolean expect) {
        // given
        final HttpPath httpPath = new HttpPath(pathString, new QueryParams(new HashMap<>()));

        // when
        final boolean result = httpPath.isRootPath();

        // then
        assertThat(result).isEqualTo(expect);
    }
}
