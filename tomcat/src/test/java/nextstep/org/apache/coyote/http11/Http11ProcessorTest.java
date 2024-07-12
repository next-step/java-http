package nextstep.org.apache.coyote.http11;

import nextstep.org.apache.coyote.http11.fixtures.TestHttpRequestMessageBuilder;
import org.junit.jupiter.api.DisplayName;
import support.StubSocket;
import org.apache.coyote.http11.Http11Processor;
import org.junit.jupiter.api.Test;

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

        // when
        doHttp11Process(socket);

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
        final var builder = new TestHttpRequestMessageBuilder();
        String httpRequest = builder.baseGetMessage()
                .emptyLine()
                .build();
        // when
        final var socket = new StubSocket(httpRequest);
        doHttp11Process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        var expected = String.join("\r\n",
            "HTTP/1.1 200 OK ",
            "Content-Type: text/html;charset=utf-8 ",
            "Content-Length: 5564 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
            "",
            actualResource("static/index.html"));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("requestLine 이 null일 경우 500.html 반환한다")
    public void requestLineNullTest() throws IOException {
        final String httpRequest= String.join("\r\n",
                "",
                "");

        final var socket = new StubSocket(httpRequest);

        // when
        doHttp11Process(socket);

        // then
        var expected = String.join("\r\n",
                "HTTP/1.1 500 Internal Server Error ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 2357 ",
                "",
                actualResource("static/500.html"));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("css 정적 리소스를 요청할 경우 content-type이 text/css 여야 한다")
    public void cssResourceRequestTest() throws IOException {

        // given
        final var builder = new TestHttpRequestMessageBuilder();
        String httpRequest = builder
                .requestLine("GET", "/css/styles.css", "HTTP/1.1")
                .acceptHeader("text/css,*/*;q=0.1")
                .emptyLine()
                .build();

        final var socket = new StubSocket(httpRequest);

        // when
        doHttp11Process(socket);

        // then
        var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 211991 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                actualResource("static/css/styles.css"));

        assertThat(socket.output()).isEqualTo(expected);
    }

    private void doHttp11Process(StubSocket socket) {
        new Http11Processor(socket).process(socket);
    }

    private String actualResource(String resourceName) throws IOException {
        final URL resource = getClass().getClassLoader().getResource(resourceName);
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }
}
