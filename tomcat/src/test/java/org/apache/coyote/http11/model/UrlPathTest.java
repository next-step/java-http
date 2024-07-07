package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class UrlPathTest {

    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "test.js, APPLICATION_JAVASCRIPT",
                    "test.html, TEXT_HTML",
                    "test.css, TEXT_CSS"
            }
    )
    void findExtension(final String urlPathString, final ContentType expect) {
        // given
        final UrlPath urlPath = new UrlPath(urlPathString);

        // when
        final ContentType contentType = urlPath.findContentType();

        // then
        assertThat(contentType).isEqualTo(expect);
    }
}
