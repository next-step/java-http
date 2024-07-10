package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpHeaderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpCookiesTest {

    @Test
    @DisplayName("생성자를 통해 초기화 할 수 있다.")
    void httpCookieCreateTest() {
        final HttpCookies httpCookies = new HttpCookies("yummy_cookie=choco");

        assertThat(httpCookies.get("yummy_cookie")).isEqualTo("choco");
    }

    @Test
    @DisplayName("생성자를 통해 초기화시 쿠키 형태가 안맞으면 예외를 던진다.")
    void httpCookieCreateErrorTest() {
        assertThatThrownBy(() -> new HttpCookies("tasty_cookie:strawberry"))
                .isInstanceOf(InvalidHttpHeaderException.class);
    }

    @Test
    @DisplayName("add 를 통해 추가 할 수 있다.")
    void testAddAndGetCookie() {
        final HttpCookies httpCookies = new HttpCookies();

        httpCookies.add("cookie1", "value1");

        assertThat(httpCookies.get("cookie1")).isEqualTo("value1");
    }

}
