package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RequestLine 뿐만 아니라, Header까지 모두 파싱하여 가진 객체를 만들자
 */
public class HttpRequestTest {
    @DisplayName("HTTP Request 메세지를 파싱하여 RequestLine과 httpHeaderMap을 가진다.")
    @Test
    void from() {
        String httpRequestMessage = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*",
                ""
        );

        HttpRequest httpRequest = HttpRequest.from(httpRequestMessage);

        assertThat(httpRequest).extracting("httpHeaderMap").isNotEqualTo(Map.of());
        assertThat(httpRequest).extracting("requestLine").isNotNull();
    }
}
