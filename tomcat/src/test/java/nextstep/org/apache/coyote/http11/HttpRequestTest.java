package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RequestLine 뿐만 아니라, Header까지 모두 파싱하여 가진 객체를 만들자
 */
public class HttpRequestTest {
    static public class HttpRequest {
        private static final int REQUEST_LINE_INDEX = 0;
        private static final int HEADER_START_INDEX = 1;
        private static final String HEADER_KEY_VALUE_SEPARATOR = ":";

        private final RequestLine requestLine;
        private final Map<String, String> httpHeaderMap;

        private HttpRequest(final RequestLine requestLine, final Map<String, String> httpHeaderMap) {
            this.requestLine = requestLine;
            this.httpHeaderMap = httpHeaderMap;
        }

        public static HttpRequest from(final String httpRequestMessage) {
            String[] httpRequestMessages = httpRequestMessage.split("\n");

            Map<String, String> collect = Arrays.stream(httpRequestMessages, HEADER_START_INDEX, httpRequestMessages.length)
                    .takeWhile(Predicate.not(String::isBlank))
                    .map(httpHeader -> httpHeader.split(HEADER_KEY_VALUE_SEPARATOR, 2))
                    .collect(Collectors.toUnmodifiableMap(
                            httpHeader -> httpHeader[0],
                            httpHeader -> httpHeader[1]
                    ));

            return new HttpRequest(RequestLine.from(httpRequestMessages[REQUEST_LINE_INDEX]), collect);
        }
    }

    @DisplayName("HTTP Request 메세지를 파싱하여 RequestLine과 httpHeaderMap을 가진다.")
    @Test
    void from() {
        String httpRequestMessage = String.join("\r\n",
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*",
                "",
                "body"
        );

        HttpRequest httpRequest = HttpRequest.from(httpRequestMessage);

        assertThat(httpRequest).extracting("httpHeaderMap").isNotEqualTo(Map.of());
        assertThat(httpRequest).extracting("requestLine").isNotNull();
    }
}
