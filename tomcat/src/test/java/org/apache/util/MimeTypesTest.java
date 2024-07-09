package org.apache.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MimeTypesTest {

    @Test
    void 확장자로_mime_type_추정한다() {
        final MimeTypes mimeTypes = new MimeTypes();

        assertThat(mimeTypes.guessByExtension(".html")).isEqualTo("text/html");
        assertThat(mimeTypes.guessByExtension(".css")).isEqualTo("text/css");
        assertThat(mimeTypes.guessByExtension(".js")).isEqualTo("application/javascript");
        assertThat(mimeTypes.guessByExtension(".svg")).isEqualTo("image/svg+xml");
    }
}
