package nextstep.org.apache.coyote.http11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.util.Objects;

class RequestLineParserTest {

    @Test
    void parser_get() {
        final String httpRequest = String.join("\r\n",
                "GET /users HTTP/1.1 ",
               "");
        final var socket = new StubSocket(httpRequest);
        final var parser = new RequestLineParser(socket);

        // when
        final var result = parser.parse();
        final var expected = new RequestLine("GET", "/users", "HTTP", "1.1");

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

}

class RequestLine {
    String method;
    String path;
    String protocol;
    String version;

    public RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol, version);
    }
}

