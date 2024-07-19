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
import java.util.UUID;

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

    @Test
    @DisplayName("GET /login 요청을 처리한다")
    public void loginRequestTest() throws IOException {

        // /login?account=gugu&password=password
        final var builder = new TestHttpRequestMessageBuilder();
        String httpRequest = builder
                .requestLine("GET", "/login", "HTTP/1.1")
                .hostHeader("localhost:8080")
                .acceptHeader("text/css,*/*;q=0.1")
                .emptyLine()
                .build();

        StubSocket socket = new StubSocket(httpRequest);
        doHttp11Process(socket);


        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 3797 ",
                "",
                actualResource("static/login.html"));

        assertThat(socket.output()).contains(expected);
    }


    @Test
    @DisplayName("POST /login 요청을 처리한다")
    public void postLoginTest() {

        // /login?account=gugu&password=password
        final var builder = new TestHttpRequestMessageBuilder();
        final UUID jsessionId = UUID.randomUUID();
        String requestBody = "account=gugu&password=password";
        String httpRequest = builder
                .requestLine("POST", "/login", "HTTP/1.1")
                .hostHeader("localhost:8080")
                .acceptHeader("text/css,*/*;q=0.1")
                .cookieHeader("JSESSIONID="+jsessionId.toString())
                .contentLength(requestBody.getBytes().length)
                .emptyLine()
                .requestBody(requestBody)
                .build();

        System.out.println("httpRequest = " + httpRequest);

        StubSocket socket = new StubSocket(httpRequest);
        doHttp11Process(socket);


        String expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Content-Type: text/html;charset=utf-8 ",
                "Location: http://localhost:8080/index.html ",
                "Set-Cookie: JSESSIONID=" + jsessionId.toString() + " "
        );

        assertThat(socket.output()).contains(expected);
    }


    @Test
    @DisplayName("/login/v2처럼 path에 버전을 명시할 경우 /login으로 요청이 처리되지 않는다")
    public void loginPathVersioningTest() {

        final var builder = new TestHttpRequestMessageBuilder();
        String httpRequest = builder
                .requestLine("GET", "/login/v2?account=gugu&password=password", "HTTP/1.1")
                .acceptHeader("text/css,*/*;q=0.1")
                .emptyLine()
                .build();

        StubSocket socket = new StubSocket(httpRequest);
        doHttp11Process(socket);


        String expected = String.join("\r\n",
                "HTTP/1.1 404 Not Found ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 0 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "",
                "");


        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("/login 로그인 실패시 /401.html로 리다이렉트한다")
    public void loginFailRedirectTo401Test() throws IOException {

        // /login?account=gugu&password=password
        final var builder = new TestHttpRequestMessageBuilder();
        String requestBody = "account=wrong&password=password";
        String httpRequest = builder
                .requestLine("POST", "/login", "HTTP/1.1")
                .hostHeader("localhost:8080")
                .acceptHeader("text/css,*/*;q=0.1")
                .contentLength(requestBody.getBytes().length)
                .emptyLine()
                .requestBody(requestBody)
                .build();

        StubSocket socket = new StubSocket(httpRequest);
        doHttp11Process(socket);


        String expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Content-Type: text/html;charset=utf-8 ",
                "Location: http://localhost:8080/401.html ");


        assertThat(socket.output()).contains(expected);
    }



    @Test
    @DisplayName("/register 요청시 register.html 반환한다")
    public void registerTest() throws IOException {

        final var builder = new TestHttpRequestMessageBuilder();
        String httpRequest = builder
                .requestLine("GET", "/register", "HTTP/1.1")
                .hostHeader("localhost:8080")
                .acceptHeader("text/css,*/*;q=0.1")
                .emptyLine()
                .build();

        StubSocket socket = new StubSocket(httpRequest);
        doHttp11Process(socket);


        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 4319 ",
                "",
                actualResource("static/register.html"));


        assertThat(socket.output()).isEqualTo(expected);
    }


    @Test
    @DisplayName("POST /register 요청이 정상적으로 처리되면 index.html 로 리다이렉트된다")
    public void registerRedirectTest() throws IOException {

        final var builder = new TestHttpRequestMessageBuilder();
        String requestBody = "account=a1&email=gutenlee@github.com&password=password";
        String httpRequest = builder
                .requestLine("POST", "/register", "HTTP/1.1")
                .hostHeader("localhost:8080")
                .acceptHeader("text/css,*/*;q=0.1")
                .contentLength(requestBody.getBytes().length)
                .emptyLine()
                .requestBody(requestBody)
                .build();

        StubSocket socket = new StubSocket(httpRequest);
        doHttp11Process(socket);


        String expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Content-Type: text/html;charset=utf-8 ",
                "Location: http://localhost:8080/index.html ",
                "Content-Length: 0 ",
                "");

        assertThat(socket.output()).contains(expected);
    }


    private void doHttp11Process(StubSocket socket) {
        new Http11Processor(socket).process(socket);
    }

    private String actualResource(String resourceName) {
        final URL resource = getClass().getClassLoader().getResource(resourceName);
        try {
            return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
