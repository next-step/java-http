package camp.nextstep;

import camp.nextstep.model.dto.RequestLine;
import camp.nextstep.model.enums.HttpMethod;
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
            assertAll("Request Line 검증",
                () -> assertEquals(HttpMethod.GET, requestLine.getMethod()),
                () -> assertEquals("/index.html", requestLine.getPath()),
                () -> assertEquals("HTTP", requestLine.getProtocol()),
                () -> assertEquals("1.1", requestLine.getVersion())
            );
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
            assertAll("Request Line 검증",
                () -> assertEquals(HttpMethod.POST, requestLine.getMethod()),
                () -> assertEquals("/user", requestLine.getPath()),
                () -> assertEquals("HTTP", requestLine.getProtocol()),
                () -> assertEquals("1.1", requestLine.getVersion())
            );
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void parseGetQueryStringRequest() {
        // given
        final String httpRequest= String.join("\r\n",
            "GET /user?userId=lsh&password=pw HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "",
            "");
        try(StubInputStream stubInputStream = new StubInputStream(httpRequest)) {
            RequestLine requestLine = RequestParser.parseRequest(stubInputStream);
            assertAll("Request Line 검증",
                () -> assertEquals(HttpMethod.GET, requestLine.getMethod()),
                () -> assertEquals("/user", requestLine.getPath()),
                () -> assertEquals("lsh", requestLine.getQueryStringMap().get("userId")),
                () -> assertEquals("pw", requestLine.getQueryStringMap().get("password")),
                () -> assertEquals("HTTP", requestLine.getProtocol()),
                () -> assertEquals("1.1", requestLine.getVersion())
            );
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}