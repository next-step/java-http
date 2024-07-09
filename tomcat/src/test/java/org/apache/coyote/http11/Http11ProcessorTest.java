package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.StubSocket;

@DisplayName("Http11Processor 클래스는")
class Http11ProcessorTest {

    @DisplayName("root 경로로 요청이 들어왔을 때 Hello world!를 응답한다.")
    @Test
    void process() {
        // given
        final var socket = new StubSocket();
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        var expected = String.join("\r\n",
            "HTTP/1.1 200 OK ",
            "Content-Type: text/html ",
            "Content-Length: 12 ",
            "",
            "Hello world!");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("index.html 파일을 읽어온다.")
    @Test
    void index() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
            "GET /index.html HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "",
            "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        var expected = String.join("\r\n",
            "HTTP/1.1 200 OK ",
            "Content-Type: text/html ",
            "Content-Length: 5564 ",
            "",
            new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("login 경로로 요청이 login.html을 읽어온다.")
    @Test
    void login() throws IOException {

        // given
        final String httpRequest = String.join("\r\n",
            "GET /login HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "",
            "");

        final var socket = new StubSocket(httpRequest);
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/login.html");
        var expected = String.join("\r\n",
            "HTTP/1.1 200 OK ",
            "Content-Type: text/html ",
            "Content-Length: 3796 ",
            "",
            new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("login이 정상적으로 처리 될 경우 index.html을 읽어온다.")
    @Test
    void login_with_query_string() throws IOException {

        // given
        final String httpRequest = String.join("\r\n",
            "GET /login?account=gugu&password=password HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "",
            "");

        final var socket = new StubSocket(httpRequest);
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        var expected = String.join("\r\n",
            "HTTP/1.1 302 Found ",
            "Content-Type: text/html ",
            "Content-Length: 5564 ",
            "",
            new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("css 경로로 요청이 style.css를 읽어온다.")
    @Test
    void css() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
            "GET /css/styles.css HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "",
            "");

        final var socket = new StubSocket(httpRequest);
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        var expected = String.join("\r\n",
            "HTTP/1.1 200 OK ",
            "Content-Type: text/css ",
            "Content-Length: 211991 ",
            "",
            new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 경로로 요청이 들어왔을 때 404.html을 응답한다.")
    @Test
    void not_found() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
            "GET /notfound HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "",
            "");

        final var socket = new StubSocket(httpRequest);
        final var processor = new Http11Processor(socket);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/404.html");
        var expected = String.join("\r\n",
            "HTTP/1.1 404 Not Found ",
            "Content-Type: text/html ",
            "Content-Length: 2426 ",
            "",
            new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }
}
