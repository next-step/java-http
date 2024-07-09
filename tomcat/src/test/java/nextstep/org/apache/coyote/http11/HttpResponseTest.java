package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.HttpProtocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.StubFile;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    static class HttpResponse {

        public static HttpResponse from(final HttpProtocol httpProtocol, final StubFile stubFile) {
            return null;
        }

        public String createFormat() {
            return null;
        }
    }

    @DisplayName("HttpProtocol, File을 받아 응답 객체를 생성하고, createFormat() 호출 시 응답 포맷 생성")
    @Test
    void of() {
        HttpProtocol httpProtocol = HttpProtocol.from("HTTP/1.1");
        StubFile stubFile = new StubFile("nextstep.html");

        HttpResponse httpResponse = HttpResponse.from(httpProtocol, stubFile);

        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: ",
                "",
                "body");

        assertThat(httpResponse.createFormat()).contains(expected);
    }
}
