package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

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
    void RequestLine에서_http_path을_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html?name=jinyoung&password=123 HTTP/1.1");
        assertAll(
                () -> assertThat(actual.getHttpPath()).isEqualTo("/docs/index.html"),
                () -> assertThat(actual.getQueryString()).isEqualTo(Map.of("name", "jinyoung", "password", "123"))
        );
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