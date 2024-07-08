package org.apache.coyote.http11;

import org.apache.coyote.HttpMethod;
import org.apache.coyote.Request;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestTest {

    @DisplayName("문자열로 받은 RequestMethod 를 HttpMethod enum 으로 변환하여 상태로 가진다")
    @Test
    public void setMethod() throws Exception {
        // given
        final Request request = new Request();

        // when
        request.setMethod("GET");

        // then
        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("QueryString 이 없는 GET 요청의 요청 경로를 상태로 가진다")
    @Test
    public void setPathByGetWithoutQueryString() throws Exception {
        // given
        final Request request = new Request();
        request.setMethod("GET");

        // when
        request.setPath("/users");

        // then
        assertThat(request.getPath()).isEqualTo("/users");
    }

    @DisplayName("QueryString 이 포함된 GET 요청의 요청 경로를 상태로 가진다")
    @Test
    public void setPathByGetWithQueryString() throws Exception {
        // given
        final Request request = new Request();
        request.setMethod("GET");

        // when
        request.setPath("/users?userId=djawnstj&password=password&name=JunSeo");

        // then
        assertAll(
                () -> assertThat(request.getPath()).isEqualTo("/users"),
                () -> assertThat(request.getParameters()).hasSize(3)
                        .containsExactlyInAnyOrderEntriesOf(Map.of("userId", "djawnstj", "password", "password", "name", "JunSeo"))
        );
    }

    @DisplayName("QueryString 이 포함된 POST 요청의 요청 경로를 상태로 가진다")
    @Test
    public void setPathByPOSTWithQueryString() throws Exception {
        // given
        final Request request = new Request();
        request.setMethod("POST");

        // when
        request.setPath("/users?userId=djawnstj&password=password&name=JunSeo");

        // then
        assertAll(
                () -> assertThat(request.getPath()).isEqualTo("/users"),
                () -> assertThat(request.getParameters()).hasSize(0)
        );
    }

    @DisplayName("요청의 프로토콜을 상태로 가진다")
    @Test
    public void setProtocol() throws Exception {
        // given
        final Request request = new Request();

        // when
        request.setProtocol("HTTP/1.1");

        // then
        assertAll(
                () -> assertThat(request.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(request.getProtocolVersion()).isEqualTo("1.1")
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
