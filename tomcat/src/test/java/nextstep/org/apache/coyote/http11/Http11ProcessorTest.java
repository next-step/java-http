package nextstep.org.apache.coyote.http11;

import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.SessionManager;
import org.apache.coyote.http11.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class Http11ProcessorTest {
    private Session session;
    private RequestHandlerMapping requestHandlerMapping;
    private RequestHandler requestHandler;

    @BeforeEach
    void setUp() {
        Manager sessionManager = SessionManager.create();
        session = Session.of("1", sessionManager);
        requestHandlerMapping = new RequestHandlerMapping();
        requestHandler = new RequestHandler(requestHandlerMapping);
    }

    @DisplayName("RequestHandler에 등록된 Controller가 없을 경우 /404.html을 응답한다")
    @Test
    void process() {
        // given
        final var socket = new StubSocket();
        final var processor = new Http11Processor(socket, requestHandler, session);

        // when
        processor.process(socket);

        // then
        assertThat(socket.output()).contains("HTTP/1.1 302 Found ", "Location: /404.html", "Content-Length: 0");
    }

    @DisplayName("RequestHandler에 등록된 Controller가 있을 경우, 그에 맞는 응답을 한다.")
    @Test
    void process2() {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /haha HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");
        final var socket = new StubSocket(httpRequest);

        requestHandlerMapping.register("/haha", new TestController());
        final var processor = new Http11Processor(socket, requestHandler, session);

        // when
        processor.process(socket);

        // then
        assertThat(socket.output()).contains("HTTP/1.1 302 Found ", "Location: /haha.html", "Content-Length: 0");
    }

    private static class TestController extends AbstractController {

        @Override
        protected void doPost(final HttpRequest request, final HttpResponse response) throws Exception {

        }

        @Override
        protected void doGet(final HttpRequest request, final HttpResponse response) throws Exception {
            response.sendRedirect("/haha.html");
        }
    }

    @DisplayName("/index.html로 요청하면 responseBody에 index.html 파일의 내용이 추가된다.")
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
        final Http11Processor processor = new Http11Processor(socket, requestHandler, session);

        // when
        processor.process(socket);

        // then
        String responseBody = getResponseBody("static/index.html");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5564 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                responseBody);

        assertThat(socket.output()).isEqualTo(expected);
    }

    @DisplayName("/css/styles.css로 요청하면 responseBody에 styles.css 파일의 내용이 추가된다.")
    @Test
    void stylesCss() throws IOException {
        // given
        final String httpRequest = String.join("\r\n",
                "GET /css/styles.css HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final Http11Processor processor = new Http11Processor(socket, requestHandler, session);

        // when
        processor.process(socket);

        // then
        String responseBody = getResponseBody("static/css/styles.css");
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 211991 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                responseBody);

        assertThat(socket.output()).isEqualTo(expected);
    }

    private String getResponseBody(String resourcePath) throws IOException {
        final URL resource = getClass().getClassLoader().getResource(resourcePath);
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }
}
