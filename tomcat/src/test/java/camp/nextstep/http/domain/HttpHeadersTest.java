package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpHeaderException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHeadersTest {

    @Test
    void ContentType_설정시_정상적으로_저장된다() {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(ContentType.HTML);

        assertThat(headers.getContentType()).isEqualTo(ContentType.HTML);
    }

    @Test
    void ContentLength_설정시_정상적으로_저장된다() {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentLength(1000L);

        assertThat(headers.getContentLength()).isEqualTo(1000L);
    }

    @Test
    void ContentLength_설정을_안하면_0을_반환한다() {
        final HttpHeaders headers = new HttpHeaders();

        assertThat(headers.getContentLength()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0})
    void ContentLength_는_0보다_커야한다(final long value) {
        final HttpHeaders headers = new HttpHeaders();

        assertThatThrownBy(() -> headers.setContentLength(value))
                .isInstanceOf(InvalidHttpHeaderException.class)
                .hasMessage("ContentLength must be grater than 0");
    }

}
