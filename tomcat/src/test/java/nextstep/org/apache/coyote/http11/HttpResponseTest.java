package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.HttpProtocol;
import org.apache.coyote.http11.HttpResponse;
import org.apache.coyote.http11.HttpStatus;
import org.apache.coyote.http11.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    @DisplayName("HttpProtocol, MediaType, responseBody를 받아 응답 객체를 생성하고, createFormat() 호출 시 응답 포맷 생성")
    @Test
    void of() {
        HttpProtocol httpProtocol = HttpProtocol.from("HTTP/1.1");

        HttpResponse httpResponse = HttpResponse.from(httpProtocol, HttpStatus.OK, MediaType.HTML, "responseBody!");

        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 13 ",
                "",
                "responseBody!");

        assertThat(httpResponse.createFormat()).contains(expected);
    }
}
