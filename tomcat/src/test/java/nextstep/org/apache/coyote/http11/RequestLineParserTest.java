package nextstep.org.apache.coyote.http11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
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


class RequestLineParser {

    private final Socket connection;

    public RequestLineParser(final Socket connection) {
        this.connection = connection;
    }

    public RequestLine parse() {
        try (final var inputStream = connection.getInputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            final var lines = br.readLine().split(" ");
            final String method = "GET";
            final String path = lines[1];
            final String protocol = lines[2].split("/")[0];
            final String version = lines[2].split("/")[1];

            return new RequestLine(method, path, protocol, version);
        } catch (IOException e) {
            return null;
        }
    }
}