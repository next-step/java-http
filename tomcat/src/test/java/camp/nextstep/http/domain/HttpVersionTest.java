package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpVersionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    void protocol_이_없으면_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpVersion("1.1"))
                .isInstanceOf(InvalidHttpVersionException.class);
    }

    @Test
    void version_이_없으면_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpVersion("HTTP"))
                .isInstanceOf(InvalidHttpVersionException.class);
    }

}
