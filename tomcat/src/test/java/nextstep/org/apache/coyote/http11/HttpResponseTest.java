package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.HttpProtocol;
import org.apache.coyote.http11.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    static class HttpResponse {
        private final HttpProtocol httpProtocol;
        private final int status;
        private final MediaType mediaType;
        private final String responseBody;

        private HttpResponse(final HttpProtocol httpProtocol, final int status, final MediaType mediaType, final String responseBody) {
            this.httpProtocol = httpProtocol;
            this.status = status;
            this.mediaType = mediaType;
            this.responseBody = responseBody;
        }

        public static HttpResponse from(final HttpProtocol httpProtocol, final MediaType mediaType, final String responseBody) {
            return new HttpResponse(httpProtocol, 200, mediaType, responseBody);
        }

        public String createFormat() {
            return String.join("\r\n",
                    "HTTP/1.1 %d OK ".formatted(status),
                    "Content-Type: %s;charset=utf-8 ".formatted(mediaType.getValue()),
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);
        }
    }

    @DisplayName("HttpProtocol, MediaType, responseBody를 받아 응답 객체를 생성하고, createFormat() 호출 시 응답 포맷 생성")
    @Test
    void of() {
        HttpProtocol httpProtocol = HttpProtocol.from("HTTP/1.1");

        HttpResponse httpResponse = HttpResponse.from(httpProtocol, MediaType.HTML, "responseBody!");

        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 13 ",
                "",
                "responseBody!");

        assertThat(httpResponse.createFormat()).contains(expected);
    }
}
