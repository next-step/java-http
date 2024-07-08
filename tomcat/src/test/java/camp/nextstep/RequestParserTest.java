package camp.nextstep;

import camp.nextstep.model.dto.RequestLine;
import org.junit.jupiter.api.Test;
import support.StubInputStream;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {

    @Test
    void parseGetRequest() {
        // given
        final String httpRequest= String.join("\r\n",
                "GET /index.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");
        try(StubInputStream stubInputStream = new StubInputStream(httpRequest)) {
            RequestLine requestLine = RequestParser.parseRequest(stubInputStream);
            assertEquals(requestLine.getMethod(), "GET");
            assertEquals(requestLine.getPath(), "/index.html");
            assertEquals(requestLine.getProtocol(), "HTTP");
            assertEquals(requestLine.getVersion(), "1.1");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void parsePostRequest() {
        // given
        final String httpRequest= String.join("\r\n",
                "POST /user HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");
        try(StubInputStream stubInputStream = new StubInputStream(httpRequest)) {
            RequestLine requestLine = RequestParser.parseRequest(stubInputStream);
            assertEquals(requestLine.getMethod(), "POST");
            assertEquals(requestLine.getPath(), "/user");
            assertEquals(requestLine.getProtocol(), "HTTP");
            assertEquals(requestLine.getVersion(), "1.1");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}