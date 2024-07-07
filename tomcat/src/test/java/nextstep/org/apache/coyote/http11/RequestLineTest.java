package nextstep.org.apache.coyote.http11;


import org.apache.coyote.http11.HttpMethod;
import org.apache.coyote.http11.RequestLine;
import org.junit.jupiter.api.DisplayName;
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
    @ParameterizedTest(name = "method = {0}")
    @EnumSource(value = HttpMethod.class)
    void 요청에_대한_RequestLine을_파싱한다(HttpMethod method) {
        final String request = """
                %s /users HTTP/1.1
                Host: www.nowhere123.com
                Accept: image/gif, image/jpeg, */*
                Accept-Language: en-us
                Accept-Encoding: gzip, deflate
                User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
                (blank line)
                """.formatted(method);

        RequestLine requestLine = RequestLine.from(request);

        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("쿼리 스트링을 파싱한다")
    @Test
    void 쿼리_스트링을_파싱한다() {
        final String request = """
                GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1
                Host: www.nowhere123.com
                Accept: image/gif, image/jpeg, */*
                Accept-Language: en-us
                Accept-Encoding: gzip, deflate
                User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
                (blank line)
                """;

        RequestLine requestLine = RequestLine.from(request);

        Map<String, String> queryStringMap = requestLine.getQueryStringMap();
        assertThat(queryStringMap).containsAllEntriesOf(Map.of(
                "userId", "javajigi",
                "password", "password",
                "name", "JaeSung"
        ));
    }
}
