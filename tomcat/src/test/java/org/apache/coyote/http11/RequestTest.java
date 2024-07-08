package org.apache.coyote.http11;

import org.apache.coyote.http.HttpMethod;
import org.apache.coyote.http.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestTest {

    @DisplayName("QueryString 이 없는 GET 요청의 RequestLine 을 상태로 가진다")
    @Test
    public void setPathByGetWithoutQueryString() throws Exception {
        // given
        final Request request = new Request();

        // when
        request.setRequestLine("GET /users HTTP/1.1");

        // then
        assertAll(
                () -> assertThat(request.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(request.getProtocolVersion()).isEqualTo("1.1"),
                () -> assertThat(request.getPath()).isEqualTo("/users"),
                () -> assertThat(request.getMethod()).isEqualTo(HttpMethod.GET)
        );
    }

    @DisplayName("QueryString 이 포함된 GET 요청의 RequestLine 을 상태로 가진다")
    @Test
    public void setPathByGetWithQueryString() throws Exception {
        // given
        final Request request = new Request();

        // when
        request.setRequestLine("GET /users?userId=djawnstj&password=password&name=JunSeo HTTP/1.1");

        // then
        assertAll(
                () -> assertThat(request.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(request.getProtocolVersion()).isEqualTo("1.1"),
                () -> assertThat(request.getPath()).isEqualTo("/users"),
                () -> assertThat(request.getParameters()).hasSize(3)
                        .containsExactlyInAnyOrderEntriesOf(Map.of("userId", "djawnstj", "password", "password", "name", "JunSeo")),
                                () -> assertThat(request.getMethod()).isEqualTo(HttpMethod.GET)
        );
    }

    @DisplayName("파라미터 이름을 통해 파라미터를 반환한다")
    @Test
    public void getParameter() throws Exception {
        // given
        final Request request = new Request();
        request.setMethod("GET");
        request.setPath("/users?userId=djawnstj");

        // when
        final String userId = request.getParameter("userId");

        // then
        assertThat(userId).isEqualTo("djawnstj");
    }

}
