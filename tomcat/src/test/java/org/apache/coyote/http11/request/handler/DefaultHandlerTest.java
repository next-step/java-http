package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.HttpRequestParser;
import org.apache.coyote.http11.model.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultHandlerTest {

    @DisplayName("별다른 로직이 없는 디폴트 핸들러 요청 테스트")
    @Test
    void handleIndexRequestTest() throws IOException {
        // given
        final List<String> request = List.of(
                "GET /index.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final HttpRequest httpRequest = HttpRequestParser.getInstance()
                .parse(request);
        final DefaultHandler defaultHandler = new DefaultHandler();
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5564 ",
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        // when
        final String response = defaultHandler.handle(httpRequest);

        // then
        assertThat(response).isEqualTo(expected);
    }
}
