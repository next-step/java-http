package org.apache.coyote.http11;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MimeTypeTest {

    @ParameterizedTest
    @CsvSource(value = {
        "TEXT_HTML, .html",
        "TEXT_CSS, .css",
        "TEXT_JS, .js",
    }, delimiter = ',')
    public void fromTest(String name, String extension) {
        assertThat(MimeType.from(extension)).isEqualTo(MimeType.valueOf(name));
    }

}