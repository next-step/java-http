package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    @Test
    void RequestLine에_status_GET이_없으면_예외가_발생한다() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new RequestLine("ERROR"));
    }

    @Test
    void RequestLine에서_status_GET을_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html HTTP/1.1");
        assertThat(actual.getHttpMethod()).isEqualTo("GET");
    }
}