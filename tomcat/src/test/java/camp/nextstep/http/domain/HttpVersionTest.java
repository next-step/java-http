package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class HttpVersionTest {

    @Test
    void http_version을_파싱한다() {
        final HttpVersion httpVersion = new HttpVersion("HTTP/1.1");

        assertSoftly(softly -> {
            softly.assertThat(httpVersion.getProtocol()).isEqualTo("HTTP");
            softly.assertThat(httpVersion.getVersion()).isEqualTo("1.1");
        });
    }

}
