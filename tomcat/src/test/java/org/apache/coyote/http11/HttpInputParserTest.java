package org.apache.coyote.http11;

import org.apache.coyote.HttpMethod;
import org.apache.coyote.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.StubSocket;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HttpInputParserTest {

    @DisplayName("GET 요청의 RequestLine 을 파싱한다")
    @Test
    public void parseGetRequestLine() throws Exception {
        // given
        final var socket = new StubSocket();
        final HttpInputParser httpInputParser = new HttpInputParser();

        // when
        httpInputParser.parseRequestLine(socket.getInputStream());

        // then
        final Request actual = httpInputParser.getRequest();
        assertAll(
                () -> assertThat(actual.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(actual.getPath()).isEqualTo("/"),
                () -> assertThat(actual.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(actual.getProtocolVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("POSt 요청의 RequestLine 을 파싱한다")
    @Test
    public void parsePostRequestLine() throws Exception {
        // given
        final var socket = new StubSocket("POST / HTTP/1.1\r\nHost: localhost:8080\r\n\r\n");
        final HttpInputParser httpInputParser = new HttpInputParser();

        // when
        httpInputParser.parseRequestLine(socket.getInputStream());

        // then
        final Request actual = httpInputParser.getRequest();
        assertAll(
                () -> assertThat(actual.getMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(actual.getPath()).isEqualTo("/"),
                () -> assertThat(actual.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(actual.getProtocolVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("HTTP 요청의 QueryString 을 파싱한다")
    @Test
    public void parseRequestQueryString() throws Exception {
        // given
        final var socket = new StubSocket("GET /users?userId=djawnstj&password=password&name=JunSeo HTTP/1.1\r\nHost: localhost:8080\r\n\r\n");
        final HttpInputParser httpInputParser = new HttpInputParser();

        // when
        httpInputParser.parseRequestLine(socket.getInputStream());

        // then
        final Request actual = httpInputParser.getRequest();
        assertAll(
                () -> assertThat(actual.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(actual.getPath()).isEqualTo("/users"),
                () -> assertThat(actual.getParameters()).hasSize(3)
                        .containsExactlyInAnyOrderEntriesOf(Map.of("userId", "djawnstj", "password", "password", "name", "JunSeo")),
                () -> assertThat(actual.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(actual.getProtocolVersion()).isEqualTo("1.1")
        );
    }

}
