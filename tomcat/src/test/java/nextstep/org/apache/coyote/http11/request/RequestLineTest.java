package nextstep.org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.request.RequestLineParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.coyote.http11.request.HttpMethod.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {
    @DisplayName("요구사항 1 - GET 요청")
    @Test
    public void test1() {
        //given
        String request = "GET /users HTTP/1.1";
        //when
        RequestLineParser requestLineParser = new RequestLineParser(request);
        RequestLine requestLine = RequestLine.from(requestLineParser);
        //then
        assertAll(
                () -> assertThat(requestLine.getHttpMethod()).isEqualTo(GET),
                () -> assertThat(requestLine.getUrlPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("요구사항 2 - POST 요청")
    @Test
    public void test2() {
        //given
        String request = "POST /users HTTP/1.1";
        //when
        RequestLineParser requestLineParser = new RequestLineParser(request);
        RequestLine requestLine = RequestLine.from(requestLineParser);
        //then
        assertAll(
                () -> assertThat(requestLine.getHttpMethod()).isEqualTo(POST),
                () -> assertThat(requestLine.getUrlPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("요구사항 3 - Query String 파싱")
    @Test
    public void test3() {
        //given
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        //when
        RequestLineParser requestLineParser = new RequestLineParser(request);
        RequestLine requestLine = RequestLine.from(requestLineParser);
        //then
        assertAll(
                () -> assertThat(requestLine.getHttpMethod()).isEqualTo(GET),
                () -> assertThat(requestLine.getUrlPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1"),
                () -> assertThat(requestLine.getQueryStrings().getQueryStringValueByKey("userId")).isEqualTo("javajigi"),
                () -> assertThat(requestLine.getQueryStrings().getQueryStringValueByKey("password")).isEqualTo("password"),
                () -> assertThat(requestLine.getQueryStrings().getQueryStringValueByKey("name")).isEqualTo("JaeSung")
        );
    }
}