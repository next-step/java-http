package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    @DisplayName("Cookie 생성 문자열로 key= 와 같이 value가 없이 들어오는 경우 value는 빈값이 된다.")
    @Test
    void createCookieWithNull() {
        Cookie cookie = Cookie.from("key=");

        assertThat(cookie.getValue()).isEmpty();
    }

    @DisplayName("name, value를 받아 앞뒤 공백을 지운 후 Cookie 객체가 생성된다")
    @Test
    void createCookieWithNameAndValue() {
        String name = "name ";
        String value = " value";

        Cookie cookie = Cookie.of(name, value);

        assertThat(cookie.equalsName(name.trim())).isTrue();
        assertThat(cookie.getValue()).isEqualTo(value.trim());
    }

    @DisplayName("name=value 형식의 문자열을 받아 Cookie 객체가 생성되고, 앞뒤 공백은 제거한다.")
    @Test
    void createCookieWithKeyValue() {
        String keyValue = " name=value ";

        Cookie cookie = Cookie.from(keyValue);

        assertThat(cookie.equalsName("name")).isTrue();
        assertThat(cookie.getValue()).isEqualTo("value");
    }
}