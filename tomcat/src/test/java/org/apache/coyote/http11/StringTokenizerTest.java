package org.apache.coyote.http11;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTokenizerTest {

    @Test
    void stringTokenizerTest() {
        // given
        final String testStr = "aaaa ssd dd";
        final String delimiter = " ";

        // when
        final String[] result = StringTokenizer.token(testStr, delimiter);

        // then
        assertThat(result.length).isEqualTo(3);
    }
}
