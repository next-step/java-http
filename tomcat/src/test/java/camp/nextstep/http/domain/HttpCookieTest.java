package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpCookieTest {
    @Test
    void 쿠키가_정확히_들어오면_키값_페어로_파싱한다() {
        // given
        String cookieStr = "yummy_cookie=choco; tasty_cookie=strawberry";

        // when
        HttpCookie httpCookie = HttpCookie.createHttpCookie(cookieStr);

        // then
        assertTrue(httpCookie.getCookieMap().size() == 2);
    }
}