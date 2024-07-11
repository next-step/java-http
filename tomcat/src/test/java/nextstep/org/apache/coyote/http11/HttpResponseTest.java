package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    @DisplayName("HttpProtocol, HttpStatus, List<HttpHeader>, responseBody를 받아 응답 객체를 생성하고, createFormat() 호출 시 응답 포맷 생성")
    @Test
    void of2() {
        HttpProtocol httpProtocol = HttpProtocol.from("HTTP/1.1");
        List<HttpHeader> httpHeaders = List.of(
                HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getValue(), MediaType.HTML.getValue() + ";charset=utf-8"),
                HttpHeader.of(HttpHeaderName.COOKIE.getValue(), "name=value")
        );

        HttpResponse httpResponse = HttpResponse.of(httpProtocol, HttpStatus.OK, httpHeaders, "responseBody!");

        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Cookie: name=value ",
                "Content-Length: 13 ",
                "",
                "responseBody!");

        assertThat(httpResponse.createFormat()).contains(expected);
    }
}
