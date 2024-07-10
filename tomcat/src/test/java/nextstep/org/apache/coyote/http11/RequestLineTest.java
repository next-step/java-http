package nextstep.org.apache.coyote.http11;


import org.apache.coyote.http11.HttpMethod;
import org.apache.coyote.http11.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

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
            assertThat(requestLine.getVersion()).isEqualTo("HTTP/1.1");
            assertThat(requestLine.hasQueryParams()).isFalse();
        }

        @DisplayName("쿼리 스트링이 있는 경우")
        @Test
        void parseRequestLine2() {
            String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ";

            RequestLine requestLine = RequestLine.from(request);

            assertThat(requestLine.getQueryParamValue("userId")).isEqualTo("javajigi");
            assertThat(requestLine.getQueryParamValue("password")).isEqualTo("password");
            assertThat(requestLine.getQueryParamValue("name")).isEqualTo("JaeSung");
        }
    }
}
