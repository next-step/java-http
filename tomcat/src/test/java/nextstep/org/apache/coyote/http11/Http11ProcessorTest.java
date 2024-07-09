package nextstep.org.apache.coyote.http11;

import camp.nextstep.servlet.LoginServlet;
import org.apache.catalina.connector.CoyoteAdapter;
import org.apache.coyote.http11.Http11Processor;
import org.apache.coyote.http11.HttpInputParser;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class Http11ProcessorTest {

    @Test
    void process() {
        // given
        final var socket = new StubSocket();
        final var adapter = new CoyoteAdapter();
        final HttpInputParser httpParser = new HttpInputParser(socket.getInputStream());
        final var processor = new Http11Processor(socket, adapter);

        // when
        processor.process(socket);

        // then
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 12 ",
                "",
                "Hello world!");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void index() throws IOException {
        // given
        final String httpRequest= String.join("\r\n",
                "GET /index.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final var adapter = new CoyoteAdapter();
        final var processor = new Http11Processor(socket, adapter);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5518 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void styles() throws IOException {
        // given
        final String httpRequest= String.join("\r\n",
                "GET /css/styles.css HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final var adapter = new CoyoteAdapter();
        final var processor = new Http11Processor(socket, adapter);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 211988 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void login() throws IOException {
        // given
        final String httpRequest= String.join("\r\n",
                "GET /login?account=gugu&password=password HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final var adapter = new CoyoteAdapter();
        adapter.addServlet("/login", new LoginServlet());
        final var processor = new Http11Processor(socket, adapter);

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/login.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 3716 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
    }
}
