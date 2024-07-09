package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.RequestLine;
import org.apache.coyote.http11.RequestParser;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        RequestLine parsed = RequestParser.parse(socket.getInputStream(), StandardCharsets.UTF_8);

        assertThat(parsed.getMethod()).isEqualTo("GET");
        assertThat(parsed.getPath()).isEqualTo("/users");
        assertThat(parsed.getProtocol()).isEqualTo("HTTP");
        assertThat(parsed.getVersion()).isEqualTo("1.1");
    }

    @Test
    public void postTest() {
        final String httpRequest= String.join("\r\n",
                "POST /users HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        RequestLine parsed = RequestParser.parse(socket.getInputStream(), StandardCharsets.UTF_8);

        assertThat(parsed.getMethod()).isEqualTo("POST");
        assertThat(parsed.getPath()).isEqualTo("/users");
        assertThat(parsed.getProtocol()).isEqualTo("HTTP");
        assertThat(parsed.getVersion()).isEqualTo("1.1");
    }


    @Test
    public void queryParamTest() {

    }


}