package study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HTTP 요청을 파싱하는 RequestLine 구현
 * RequestLine = HTTP 요청의 첫 번째 라인을 의미
 * ex: GET /docs/index.html HTTP/1.1
 */
public class RequestLineTest {
    static class RequestLine {
        private final String method;
        private final String path;
        private final String protocol;
        private final String version;

        private RequestLine(final String method, final String path, final String protocol, final String version) {
            this.method = method;
            this.path = path;
            this.protocol = protocol;
            this.version = version;
        }

        public static RequestLine from(final String request) {
            String[] requests = request.split("\n");
            String requestLine = requests[0];
            String[] requestLines = requestLine.split(" ");

            String[] protocolAndVersion = requestLines[2].split("/");
            return new RequestLine(requestLines[0], requestLines[1], protocolAndVersion[0], protocolAndVersion[1]);
        }

        public String getMethod() {
            return method;
        }

        public String getPath() {
            return path;
        }

        public String getProtocol() {
            return protocol;
        }

        public String getVersion() {
            return version;
        }
    }

    @ParameterizedTest(name = "method = {0}")
    @ValueSource(strings = {"GET", "POST"})
    void 요청에_대한_RequestLine을_파싱한다(String method) {
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
}
