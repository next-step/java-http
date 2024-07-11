package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.constant.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestParserTest {

    @DisplayName("HttpRequestParser를 통해 HttpRequest 객체를 생성한다.")
    @Test
    void parseTest() throws IOException {
        // given
        final List<String> request = List.of(
                "POST /register.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Length: 80 ",
                "Content-Type: application/x-www-form-urlencoded ",
                "Accept: */* ",
                "",
                "account=gugu&password=password&email=hkkang%40woowahan.com");

        final HttpHeaders httpHeaders = HttpRequestHeaderParser.getInstance()
                .parse(request);

        // when
        final HttpRequest result = HttpRequestParser.getInstance()
                .parse(httpHeaders, "account=gugu&password=password&email=hkkang%40woowahan.com");

        // then
        assertThat(result.httpRequestHeader().requestLine().httpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(result.requestBody().valueBy("account")).isEqualTo("gugu");
        assertThat(result.requestBody().valueBy("email")).isEqualTo("hkkang@woowahan.com");
    }

    @DisplayName("reqeustBody가 빈 값인 경우 HttpRequest 객체의 requestbody는 비어있다.")
    @Test
    void parseEmptyBodyTest() throws IOException {
        // given
        final List<String> request = List.of(
                "POST /register.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "Content-Length: 80 ",
                "Content-Type: application/x-www-form-urlencoded ",
                "Accept: */* ",
                "",
                "");
        final HttpHeaders httpHeaders = HttpRequestHeaderParser.getInstance()
                .parse(request);

        // when

        final HttpRequest result = HttpRequestParser.getInstance()
                .parse(httpHeaders, "");
        // then
        assertThat(result.httpRequestHeader().requestLine().httpMethod()).isEqualTo(HttpMethod.POST);
        assertThat(result.requestBody().isEmpty()).isTrue();
    }
}
