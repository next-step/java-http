package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    @Test
    void RequestLine이_포맷이_맞지_않으면_예외가_발생한다() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new RequestLine("GET /docs/index.html"));
    }

    @Test
    void RequestLine에_status_GET이_없으면_예외가_발생한다() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new RequestLine("ERROR /docs/index.html HTTP/1.1"));
    }

    @Test
    void RequestLine에서_status_GET을_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html HTTP/1.1");
        assertThat(actual.getHttpMethod()).isEqualTo("GET");
    }

    @Test
    void RequestLine에서_http_url을_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html HTTP/1.1");
        assertThat(actual.getHttpUrl()).isEqualTo("/docs/index.html");
    }

    @Test
    void RequestLine에서_protocol과_version이_없는_경우_예외가_발생한다() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new RequestLine("GET /docs/index.html HTTP"));
    }

    @Test
    void RequestLine에서_protocol을_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html HTTP/1.1");
        assertThat(actual.getProtocol()).isEqualTo("HTTP");
    }

    @Test
    void RequestLine에서_protocol_version을_파싱한다() {
        RequestLine actual = new RequestLine("GET /docs/index.html HTTP/1.1");
        assertThat(actual.getProtocolVersion()).isEqualTo("1.1");
    }
}