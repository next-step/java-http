package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.Http11Processor;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class Http11ProcessorTest {

    private static final String HTTP_GET_REQUEST_TEMPLATE =
            "GET %s HTTP/1.1 \r\n" +
                    "Host: localhost:8080 \r\n" +
                    "Connection: keep-alive \r\n" +
                    "\r\n";

    private static final String HTTP_POST_REQUEST_TEMPLATE =
            "POST %s HTTP/1.1 \r\n" +
                    "Host: localhost:8080 \r\n" +
                    "Connection: keep-alive \r\n" +
                    "Content-Length: %d \r\n" +
                    "\r\n" +
                    "%s";

    @Test
    void process() {
        // given
        final var socket = new StubSocket();
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 12 ",
                "",
                "Hello world!");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void process_index() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/index.html");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5670 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void process_wrong_page() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/wrong_page.html");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/404.html");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 2477 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void process_css() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/css/styles.css");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 223255 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void process_js() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/js/scripts.js");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/js/scripts.js");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/javascript;charset=utf-8 ",
                "Content-Length: 1002 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void process_login() {
        // given
        final String body = "/login?account=gugu&password=password";
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, body);
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Location: /index.html ",
                "",
                "");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void process_login_failed() {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/login?account=gugu&password=wrong");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Location: /401.html ",
                "",
                "");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void process_register_get() throws IOException {
        // given
        final String httpRequest = String.format(HTTP_GET_REQUEST_TEMPLATE, "/register");
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/register.html");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 4391 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void process_register_post() {
        // given
        final String body = "account=test&password=password&email=test@test.com";
        final String httpRequest = String.format(HTTP_POST_REQUEST_TEMPLATE, "/register", body.length(), body);
        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Location: /index.html ",
                "",
                "");

        assertThat(socket.output()).isEqualTo(expected);
    }
}
