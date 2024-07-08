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
                () -> assertThat(result).containsExactly("aaaa", "ssd", "dd")
        );
    }
}
