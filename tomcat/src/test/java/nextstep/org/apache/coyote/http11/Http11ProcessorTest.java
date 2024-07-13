package nextstep.org.apache.coyote.http11;

import org.apache.catalina.Session;
import org.apache.coyote.http11.Http11Processor;
import org.apache.coyote.http11.SessionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

class Http11ProcessorTest {

    public static final String givenSessionId = UUID.randomUUID().toString();

    @BeforeAll
    static void setUpSessionManager() {
        SessionManager.INSTANCE.add(new Session(givenSessionId));
    }

    @Test
    void helloWorld() {
        // given
        final String httpRequest = String.join("\r\n",
                "GET / HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: JSESSIONID=" + givenSessionId,
                "");

        final var socket = new StubSocket(httpRequest);
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "HELLO: World! ",
                "Content-Length: 12 ",
                "Content-Type: text/html;charset=utf-8 ",
                "",
                "Hello world!");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void index() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /index.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: JSESSIONID=" + givenSessionId,
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Length: 5564 ",
                "Content-Type: text/html;charset=utf-8 ",
                "",
                new String(Files.readAllBytes(new File(requireNonNull(resource).getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void css_파일_렌더_테스트() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /css/styles.css HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: JSESSIONID=" + givenSessionId,
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Length: 211991 ",
                "Content-Type: text/css;charset=utf-8 ",
                "",
                new String(Files.readAllBytes(new File(requireNonNull(resource).getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }


    @Test
    void js_파일_렌더_테스트() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /assets/chart-area.js HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: JSESSIONID=" + givenSessionId,
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/assets/chart-area.js");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Length: 1530 ",
                "Content-Type: application/javascript;charset=utf-8 ",
                "",
                new String(Files.readAllBytes(new File(requireNonNull(resource).getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void render404_404_페이지를_그린다() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /missing-file.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Cookie: JSESSIONID=" + givenSessionId,
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/404.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 404 Not Found ",
                "Content-Length: 2426 ",
                "Content-Type: text/html;charset=utf-8 ",
                "",
                new String(Files.readAllBytes(new File(requireNonNull(resource).getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }
}
