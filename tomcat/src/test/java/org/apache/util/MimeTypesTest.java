package org.apache.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MimeTypesTest {

    @Test
    void 확장자로_mime_type_추정한다() {
        assertThat(MimeTypes.guessByExtension(".html")).isEqualTo("text/html");
        assertThat(MimeTypes.guessByExtension(".css")).isEqualTo("text/css");
        assertThat(MimeTypes.guessByExtension(".js")).isEqualTo("application/javascript");
        assertThat(MimeTypes.guessByExtension(".svg")).isEqualTo("image/svg+xml");
    }
}
