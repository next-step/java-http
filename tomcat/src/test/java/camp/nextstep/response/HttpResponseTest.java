package camp.nextstep.response;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.request.HttpRequestCookie;
import camp.nextstep.request.HttpRequestParser;
import camp.nextstep.staticresource.StaticResourceLoader;
import org.apache.catalina.Session;
import org.apache.coyote.http11.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class HttpResponseTest {
    private final String givenSessionId = "abc";
    final String httpRequestString = String.join("\r\n",
            "GET / HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "Cookie: JSESSIONID=" + givenSessionId,
            "");

    @BeforeEach
    void setUp() {
        SessionManager.INSTANCE.add(new Session(givenSessionId));
    }

    @Test
    void renderStaticResource() throws IOException {
        final var socket = new StubSocket(httpRequestString);
        HttpResponse response = buildResponse(socket);

        response.render("/index.html");

        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Length: 5564 ",
                "Content-Type: text/html;charset=utf-8 ",
                "",
                readFileContent("static/index.html"));
        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void renderNotFound() throws IOException {
        final var socket = new StubSocket(httpRequestString);
        HttpResponse response = buildResponse(socket);

        response.render(ResponseStatusCode.NotFound, "/404.html");

        String expected = String.join("\r\n",
                "HTTP/1.1 404 Not Found ",
                "Content-Length: 2426 ",
                "Content-Type: text/html;charset=utf-8 ",
                "",
                readFileContent("static/404.html"));
        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void redirectTo() throws IOException {
        final var socket = new StubSocket(httpRequestString);
        HttpResponse response = buildResponse(socket);

        response.redirectTo("/abc.html");

        String expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Location: " + "/abc.html ",
                ""
        );
        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void addNewCookies() throws IOException {
        final var socket = new StubSocket(httpRequestString);
        HttpResponse response = buildResponse(socket);
        response.setCookie(new HttpRequestCookie("abc", "def"));
        response.setCookie(new HttpRequestCookie("ghi", "jkl"));
        response.render("/index.html");

        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Length: 5564 ",
                "Content-Type: text/html;charset=utf-8 ",
                "Set-Cookie: abc=def; Path=/ ",
                "Set-Cookie: ghi=jkl; Path=/ ",
                "",
                readFileContent("static/index.html"));
        assertThat(socket.output()).isEqualTo(expected);
    }

    private HttpResponse buildResponse(StubSocket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        HttpRequest request = new HttpRequestParser().parse(bufferedReader);
        OutputStream outputStream = socket.getOutputStream();
        return new HttpResponse(outputStream, request, new StaticResourceLoader());
    }

    private String readFileContent(String fileName) throws IOException {
        URL resource = getClass().getClassLoader().getResource(fileName);
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }
}
