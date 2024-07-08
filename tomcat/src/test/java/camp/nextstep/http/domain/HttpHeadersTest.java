package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {

    @Test
    void ContentType_설정시_정상적으로_저장된다() {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType("text/html");

        assertThat(headers.getContentType()).isEqualTo("text/html");
    }
}
