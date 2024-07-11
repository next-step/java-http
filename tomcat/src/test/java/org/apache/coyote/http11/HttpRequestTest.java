package org.apache.coyote.http11;

import org.apache.coyote.http.HttpMethod;
import org.apache.coyote.http.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpRequestTest {

    @DisplayName("QueryString 이 없는 GET 요청의 RequestLine 을 상태로 가진다")
    @Test
    public void setPathByGetWithoutQueryString() throws Exception {
        // given
        final HttpRequest httpRequest = new HttpRequest();

        // when
        httpRequest.setRequestLine("GET /users HTTP/1.1");

        // then
        assertAll(
                () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(httpRequest.getProtocolVersion()).isEqualTo("1.1"),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/users"),
                () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET)
        );
    }

    @DisplayName("QueryString 이 포함된 GET 요청의 RequestLine 을 상태로 가진다")
    @Test
    public void setPathByGetWithQueryString() throws Exception {
        // given
        final HttpRequest httpRequest = new HttpRequest();

        // when
        httpRequest.setRequestLine("GET /users?userId=djawnstj&password=password&name=JunSeo HTTP/1.1");

        // then
        assertAll(
                () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(httpRequest.getProtocolVersion()).isEqualTo("1.1"),
                () -> assertThat(httpRequest.getPath()).isEqualTo("/users"),
                () -> assertThat(httpRequest.getParameters()).hasSize(3)
                        .containsExactlyInAnyOrderEntriesOf(Map.of("userId", "djawnstj", "password", "password", "name", "JunSeo")),
                                () -> assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET)
        );
    }

}
