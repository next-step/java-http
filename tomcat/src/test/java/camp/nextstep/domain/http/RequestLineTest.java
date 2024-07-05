package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class RequestLineTest {

    @Test
    void RequestLine이_포맷이_맞지_않으면_예외가_발생한다() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new RequestLine("GET /docs/index.html"));
    }

    @Test
    void RequestLine에서_HttpMethod를_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html HTTP/1.1");
        assertThat(actual.getHttpMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void RequestLine에서_http_url을_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html HTTP/1.1");
        assertThat(actual.getHttpUrl()).isEqualTo("/docs/index.html");
    }

    @Test
    void RequestLine에서_HttpProtocol을_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html HTTP/1.1");
        assertAll(
                () -> assertThat(actual.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(actual.getProtocolVersion()).isEqualTo("1.1")
        );
    }
}