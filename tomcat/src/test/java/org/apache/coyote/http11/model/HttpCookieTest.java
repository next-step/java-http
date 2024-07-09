package org.apache.coyote.http11.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpCookieTest {

    @DisplayName("HttpCookie 객체를 생성하는 테스트")
    @Test
    void fromStringLineTest() {
        // given
        final String line = "yummy_cookie=choco; tasty_cookie=strawberry; JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46";

        // when
        final HttpCookie httpCookie = HttpCookie.fromStringLine(line);

        // then
        assertAll(
                () -> assertThat(httpCookie.valueByKey("yummy_cookie")).isEqualTo("choco"),
                () -> assertThat(httpCookie.valueByKey("tasty_cookie")).isEqualTo("strawberry"),
                () -> assertThat(httpCookie.valueByKey("JSESSIONID")).isEqualTo("656cef62-e3c4-40bc-a8df-94732920ed46")
        );
    }

    @DisplayName("HttpCookie 객체에서 cookie를 String Line으로 조회하는 테스트")
    @Test
    void valueToLine() {
        // given
        final String line = "Cookie: yummy_cookie=choco; JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46; tasty_cookie=strawberry";
        final HttpCookie httpCookie = HttpCookie.fromStringLine(line);

        // when
        final String result = httpCookie.toLine();

        // then
        assertThat(result).isEqualTo(line);
    }
}
