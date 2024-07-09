package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.HttpRequestHeaderParser;
import org.apache.coyote.http11.HttpRequestParser;
import org.apache.coyote.http11.model.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NotFoundHandlerTest {

    @DisplayName("매핑되는 path가 없는 경우 핸들링 테스트")
    @Test
    void handleNotFoundRequestTest() throws IOException {
        // given
        final List<String> request = List.of(
                "GET /notfound.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final HttpRequest httpRequest = HttpRequestParser.getInstance()
                .parse(HttpRequestHeaderParser.getInstance().parse(request), "");

        final RequestHandler notFoundHandler = new NotFoundHandler();
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 12 ",
                "",
                "Hello world!");
        // when
        final String response = notFoundHandler.handle(httpRequest);

        // then
        assertThat(response).isEqualTo(expected);
    }
}
