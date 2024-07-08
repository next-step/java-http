package nextstep.org.apache.coyote.http11;


import org.apache.coyote.http11.HttpMethod;
import org.apache.coyote.http11.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HTTP 요청을 파싱하는 RequestLine 구현
 * RequestLine = HTTP 요청의 첫 번째 라인을 의미
 * ex: GET /docs/index.html HTTP/1.1
 */
public class RequestLineTest {
    @DisplayName("RequestLine 파싱 테스트")
    @Nested
    class ParseTest {
        @DisplayName("쿼리 스트링이 없는 경우")
        @ParameterizedTest(name = "method = {0}")
        @EnumSource(value = HttpMethod.class)
        void parseRequestLine(HttpMethod method) {
            String strRequestLine = "%s /users HTTP/1.1".formatted(method);

            RequestLine requestLine = RequestLine.from(strRequestLine);

            assertThat(requestLine.getMethod()).isEqualTo(method);
            assertThat(requestLine.getPath()).isEqualTo("/users");
            assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
            assertThat(requestLine.getVersion()).isEqualTo("1.1");
            assertThat(requestLine.getQueryStringMap()).isEmpty();
        }

        @DisplayName("쿼리 스트링이 있는 경우")
        @Test
        void parseRequestLine2() {
            String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ";

            RequestLine requestLine = RequestLine.from(request);

            assertThat(requestLine.getQueryStringMap()).containsAllEntriesOf(Map.of(
                    "userId", "javajigi",
                    "password", "password",
                    "name", "JaeSung"
            ));
        }
    }
}
