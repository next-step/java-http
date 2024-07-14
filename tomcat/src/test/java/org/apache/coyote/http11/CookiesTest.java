package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {
    @DisplayName("Cookies 생성 시 ;로 구분된 쿠키들을 Cookie 객체로 파싱한다")
    @Test
    void from() {
        String cookies = "JSESSIONID=123; foo=bar; next=step ";

        Cookies parsedCookies = Cookies.from(cookies);

        assertThat(parsedCookies).hasSize(3);
    }

    @DisplayName("Cookies 생성 시 문자열로 빈 값이 넘어오면 빈 리스트를 가진 객체가 생성된다")
    @Test
    void from2() {
        String cookies = "";

        Cookies parsedCookies = Cookies.from(cookies);

        assertThat(parsedCookies).isEmpty();
    }
}