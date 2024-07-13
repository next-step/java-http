package nextstep.org.apache.coyote.http11;

import camp.nextstep.servlet.LoginServlet;
import org.apache.catalina.connector.CoyoteAdapter;
import org.apache.coyote.http11.Http11Processor;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import support.StubSocket;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class Http11ProcessorTest {

    @Test
    void process() {
        // given
        final var socket = new StubSocket();
        final var adapter = new CoyoteAdapter();
        final var processor = new Http11Processor(socket, adapter);

        // when
        processor.process(socket);

        // then
        var expected = String.join(System.lineSeparator(),
                "HTTP/1.1 500 Internal Server Error ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 30 ",
                "",
                "ERROR: Could not find resource");

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    void styles() throws IOException {
        // given
        final String httpRequest= String.join(System.lineSeparator(),
                "GET /css/styles.css HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        final var socket = new StubSocket(httpRequest);
        final var adapter = new CoyoteAdapter();
        final var processor = new Http11Processor(socket, adapter);
        final MockedStatic<UUID> mockedUUID = mockStatic(UUID.class);
        final UUID mockUUID = mock(UUID.class);
        mockedUUID.when(UUID::randomUUID).thenReturn(mockUUID);
        when(mockUUID.toString()).thenReturn("test-uuid");

        // when
        processor.process(socket);

        // then
        final URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        var expected = String.join(System.lineSeparator(),
                "HTTP/1.1 200 OK ",
                "Content-Type: text/css;charset=utf-8 ",
                "Content-Length: 211988 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                "Set-Cookie: JSESSIONID=test-uuid ",
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(socket.output()).isEqualTo(expected);
        mockedUUID.close();
    }
}
