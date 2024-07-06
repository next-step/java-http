package nextstep.org.apache.coyote.http11;

import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

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

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.method).isEqualTo(RequestLine.Method.GET);
            softly.assertThat(result.path).isEqualTo("/users");
            softly.assertThat(result.protocol).isEqualTo("HTTP");
            softly.assertThat(result.version).isEqualTo("1.1");
        });
    }

    @Test
    void parser_post() {
        final String httpRequest = String.join("\r\n",
                "POST /users HTTP/1.1 ",
                "");
        final var socket = new StubSocket(httpRequest);
        final var parser = new RequestLineParser(socket);

        // when
        final var result = parser.parse();

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.method).isEqualTo(RequestLine.Method.POST);
            softly.assertThat(result.path).isEqualTo("/users");
            softly.assertThat(result.protocol).isEqualTo("HTTP");
            softly.assertThat(result.version).isEqualTo("1.1");
        });

    }

    @Test
    void parser_params() {
        final String httpRequest = String.join("\r\n",
                "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ",
                "");
        final var socket = new StubSocket(httpRequest);
        final var parser = new RequestLineParser(socket);

        // when
        final var result = parser.parse();

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.method).isEqualTo(RequestLine.Method.GET);
            softly.assertThat(result.path).isEqualTo("/users");
            softly.assertThat(result.protocol).isEqualTo("HTTP");
            softly.assertThat(result.version).isEqualTo("1.1");
            softly.assertThat(result.params.get("userId")).isEqualTo("javajigi");
            softly.assertThat(result.params.get("password")).isEqualTo("password");
            softly.assertThat(result.params.get("name")).isEqualTo("JaeSung");
        });
    }


}

class RequestLine {
    Method method;
    String path;
    String protocol;
    String version;
    Map<String, String> params;

    enum Method {
        GET, POST,
    }

    public RequestLine(Method method, String path, String protocol, String version, Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.params = params;
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
            final RequestLine.Method method = RequestLine.Method.valueOf(lines[0]);
            final String path = lines[1].split("\\?")[0];
            final var params = parseParams(lines[1]);
            final String protocol = lines[2].split("/")[0];
            final String version = lines[2].split("/")[1];

            return new RequestLine(method, path, protocol, version, params);
        } catch (IOException e) {
            return null;
        }
    }

    private Map<String, String> parseParams(final String input) {
        final var maps = new HashMap<String, String>();
        if (!input.contains("?")) return maps;
        final var params = input.split("\\?")[1].split("&");
        for (String param : params) {
            final String key = param.split("=")[0];
            final String value = param.split("=")[1];
            maps.put(key, value);
        }
        return maps;
    }
}