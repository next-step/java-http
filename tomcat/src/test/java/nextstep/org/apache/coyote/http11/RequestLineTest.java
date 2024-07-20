package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.request.HttpMethod;
import org.apache.coyote.http11.request.QueryString;
import org.apache.coyote.http11.request.RequestParser;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @Test
    public void getTest() throws IOException {

        final String httpRequest= String.join("\r\n",
                "GET /users HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final var request = RequestParser.parse(socket.getInputStream(), StandardCharsets.UTF_8);
        final var parsed = request.getRequestLine();

        assertThat(parsed.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(parsed.getPath()).isEqualTo("/users");
        assertThat(parsed.getProtocol()).isEqualTo("HTTP");
        assertThat(parsed.getVersion()).isEqualTo("1.1");
    }

    @Test
    public void postTest() throws IOException {
        final String httpRequest= String.join("\r\n",
                "POST /users HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final var request = RequestParser.parse(socket.getInputStream(), StandardCharsets.UTF_8);
        final var parsed = request.getRequestLine();

        assertThat(parsed.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(parsed.getPath()).isEqualTo("/users");
        assertThat(parsed.getProtocol()).isEqualTo("HTTP");
        assertThat(parsed.getVersion()).isEqualTo("1.1");
    }


    @Test
    public void queryParamTest() throws IOException {
        final String httpRequest= String.join("\r\n",
                "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final var request = RequestParser.parse(socket.getInputStream(), StandardCharsets.UTF_8);
        final var parsed = request.getRequestLine();

        assertThat(parsed.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(parsed.getPath()).isEqualTo("/users");
        assertThat(parsed.getProtocol()).isEqualTo("HTTP");
        assertThat(parsed.getVersion()).isEqualTo("1.1");
        assertThat(parsed.getQueryString())
                .isEqualTo(new QueryString("userId=javajigi&password=password&name=JaeSung"));
    }


}
