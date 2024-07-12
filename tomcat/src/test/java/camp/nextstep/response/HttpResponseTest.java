package camp.nextstep.response;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.request.HttpRequestParser;
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
    private SessionManager sessionManager;
    final String httpRequestString = String.join("\r\n",
            "GET / HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "Cookie: JSESSIONID=" + givenSessionId,
            "");

    @BeforeEach
    void setUp() {
        sessionManager = SessionManager.INSTANCE;
        sessionManager.add(new Session(givenSessionId));
    }

    @Test
    void renderStaticResource() throws IOException {
        final var socket = new StubSocket(httpRequestString);
        HttpResponse response = buildResponse(socket);

        response.renderStaticResource("/index.html");

        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 5564 ",
                "",
                readFileContent("static/index.html"));
        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void render404() throws IOException {
        final var socket = new StubSocket(httpRequestString);
        HttpResponse response = buildResponse(socket);

        response.render404();

        String expected = String.join("\r\n",
                "HTTP/1.1 404 Not Found ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 2426 ",
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

    private HttpResponse buildResponse(StubSocket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        HttpRequest request = new HttpRequestParser().parse(bufferedReader);
        OutputStream outputStream = socket.getOutputStream();
        return new HttpResponse(request, outputStream);
    }

    private String readFileContent(String fileName) throws IOException {
        URL resource = getClass().getClassLoader().getResource(fileName);
        return new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
    }
}
