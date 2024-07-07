package org.apache.coyote.http11.model;

import org.apache.coyote.http11.model.constant.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class UrlPathTest {

    @DisplayName("urlpath를 반환한다. 확장자가 없다면 .html을 포함해서 반환한다.")
    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "/test.js, /test.js",
                    "/test.html, /test.html",
                    "/test.css, /test.css",
                    "/test, /test.html",
            }
    )
    void urlPathTest(final String urlPathString, final String expect) {
        // given
        final UrlPath urlPath = new UrlPath(urlPathString);

        // when
        final String result = urlPath.urlPath();

        // then
        assertThat(result).isEqualTo(expect);
    }

    @DisplayName("urlPath에서 ContentType을 추출한다.")
    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "/test.js, APPLICATION_JAVASCRIPT",
                    "/test.html, TEXT_HTML",
                    "/test.css, TEXT_CSS"
            }
    )
    void findContentType(final String urlPathString, final ContentType expect) {
        // given
        final UrlPath urlPath = new UrlPath(urlPathString);

        // when
        final ContentType contentType = urlPath.findContentType();

        // then
        assertThat(contentType).isEqualTo(expect);
    }
}
