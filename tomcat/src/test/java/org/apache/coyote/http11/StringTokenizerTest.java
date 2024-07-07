package org.apache.coyote.http11;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class StringTokenizerTest {

    @Test
    void stringTokenizerTest() {
        // given
        final String testStr = "aaaa ssd dd";
        final String delimiter = " ";

        // when
        final String[] result = StringTokenizer.token(testStr, delimiter);

        // then
        assertAll(
                () -> assertThat(result.length).isEqualTo(3),
                () -> assertThat(result[0]).isEqualTo("aaaa"),
                () -> assertThat(result[1]).isEqualTo("ssd"),
                () -> assertThat(result[2]).isEqualTo("dd")
        );
    }
}
