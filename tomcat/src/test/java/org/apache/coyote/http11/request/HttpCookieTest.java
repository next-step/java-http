package org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HttpCookie 클래스는")
class HttpCookieTest {

    @DisplayName("쿠키 헤더를 입력받아 쿠키 객체를 생성한다.")
    @Test
    void from() {
        // given
        String cookieHeader = "JSESSIONID=1234;name=gugu";

        // when
        HttpCookie httpCookie = HttpCookie.from(cookieHeader);

        // then
        assertThat(httpCookie.getJSessionId()).isEqualTo("1234");
        assertThat(httpCookie.getCookie("name")).isEqualTo("gugu");
    }

    @DisplayName("쿠키 객체를 쿠키 헤더로 변환한다.")
    @Test
    void toCookieHeader() {
        // given
        String cookieHeader = "JSESSIONID=1234;name=gugu";
        HttpCookie httpCookie = HttpCookie.from(cookieHeader);

        // when
        String result = httpCookie.toCookieHeader();

        // then
        assertThat(cookieHeader).isEqualTo(result);
    }
}
