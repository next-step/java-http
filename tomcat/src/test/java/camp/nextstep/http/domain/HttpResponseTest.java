package camp.nextstep.http.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    private OutputStream outputStream;
    private HttpResponse httpResponse;

    @BeforeEach
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        httpResponse = new HttpResponse(outputStream);
    }

    @Test
    @DisplayName("헤더를 추가할 수 있다")
    public void addHeader_test() throws IOException {
        httpResponse.addHeader("Content-Type", "text/html");
        httpResponse.forwardBody("Hello, World!");

        final String response = outputStream.toString();
        assertThat(response).contains("Content-Type: text/html");
        assertThat(response).contains("Hello, World!");
    }

    @Test
    @DisplayName("forward 를 통해 resource 를 응답해줄 수 있다")
    public void forwardTest() throws IOException {
        httpResponse.forward("/index.html");

        final String response = outputStream.toString();

        // then
        final URL resource = getClass().getClassLoader().getResource("static/index.html");
        assertThat(response).contains("HTTP/1.1 200 OK");
        assertThat(response).contains(new String(Files.readAllBytes(new File(resource.getFile()).toPath())));
    }

    @Test
    @DisplayName("forwardBody 를 통해 단순 문자열을 응답해줄 수 있다")
    public void forwardBodyTest() throws IOException {
        httpResponse.forwardBody("Hello, World!");

        final String response = outputStream.toString();
        assertThat(response).contains("HTTP/1.1 200 OK");
        assertThat(response).contains("Content-Length: 13");
        assertThat(response).contains("Hello, World!");
    }

    @Test
    @DisplayName("sendRedirect 로 Redirect 응답을 해줄 수 있다")
    public void sendRedirectTest() throws IOException {
        httpResponse.sendRedirect("/new-location");

        final String response = outputStream.toString();
        assertThat(response).contains("HTTP/1.1 302 Found");
        assertThat(response).contains("Location: /new-location");
    }
}
