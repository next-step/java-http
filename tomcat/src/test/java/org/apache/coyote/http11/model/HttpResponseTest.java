package org.apache.coyote.http11.model;

import org.apache.coyote.http11.HttpRequestHeaderParser;
import org.apache.coyote.http11.model.constant.HttpStatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @DisplayName("HttpResponse에서 OK 상태 코드를 가진 응답을 생성한다.")
    @Test
    void buildResponseTest() throws IOException {
        // given
        final List<String> request = List.of(
                "POST /register.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Length: 80 ",
                "Content-Type: application/x-www-form-urlencoded ",
                "Accept: */* ",
                "",
                "test");
        final HttpRequestHeader httpRequestHeader = HttpRequestHeaderParser.getInstance()
                .parse(request);
        final HttpResponse httpResponse = new HttpResponse(HttpStatusCode.OK, httpRequestHeader, "test");

        // when
        final String result = httpResponse.buildOkResponse();

        // then
        assertThat(result)
                .isEqualTo(String.join("\r\n",
                        "HTTP/1.1 200 OK ",
                        "Content-Type: text/html;charset=utf-8 ",
                        "Content-Length: 4 ",
                        "",
                        "test"));
    }

    @DisplayName("HttpResponse에서 Redirect 상태 코드를 가진 응답을 생성한다.")
    @Test
    void buildRedirectResponseTest() throws IOException {
        // given
        final List<String> request = List.of(
                "POST /register.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Length: 80 ",
                "Content-Type: application/x-www-form-urlencoded ",
                "Accept: */* ",
                "",
                "test");
        final HttpRequestHeader httpRequestHeader = HttpRequestHeaderParser.getInstance()
                .parse(request);
        final HttpResponse httpResponse = new HttpResponse(HttpStatusCode.FOUND, httpRequestHeader, "test");

        // when
        final String result = httpResponse.buildRedirectResponse("http://localhost:8080/index.html");

        // then
        assertThat(result)
                .isEqualTo(String.join("\r\n",
                        "HTTP/1.1 302 FOUND ",
                        "Location: http://localhost:8080/index.html ",
                        "",
                        "test"));
    }
}
