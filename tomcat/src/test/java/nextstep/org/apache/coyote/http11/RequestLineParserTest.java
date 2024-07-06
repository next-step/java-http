package nextstep.org.apache.coyote.http11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import support.StubSocket;

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
