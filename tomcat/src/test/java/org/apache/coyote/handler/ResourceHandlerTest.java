package org.apache.coyote.handler;

import org.apache.coyote.http11.Http11Processor;
import org.apache.http.HttpMethod;
import org.apache.http.HttpPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

class ResourceHandlerTest {

    private final Handler handler = new ResourceHandler();

    @Test
    void handle_html_response() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/index.html"));

        var response = handler.handle(request);

        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5564 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        Assertions.assertThat(response).hasToString(expected);
    }

    @Test
    void handle_css_response() throws IOException {
        var request = new StubHttpRequest(new HttpPath("/css/styles.css"));

        var response = handler.handle(request);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 211991 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        Assertions.assertThat(response).hasToString(expected);
    }
}