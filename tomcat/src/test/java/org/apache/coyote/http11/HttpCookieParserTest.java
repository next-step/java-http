package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpCookie;
import org.apache.coyote.http11.model.HttpHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieParserTest {

    @Test
    @DisplayName("헤더에 Cookie 가 존재할 경우 파싱하여 HttpCookie 객체를 만든다")
    void parse_withCookieHeaders() {
        final HttpHeaders headers = new HttpHeaders(Map.of("Cookie", "JSESSIONID=5C4440E0AB29FAF99B5BBF499DD42B55"));

        final HttpCookie actual = HttpCookieParser.parse(headers);

        assertThat(actual.get("JSESSIONID")).isEqualTo("5C4440E0AB29FAF99B5BBF499DD42B55");
    }

    @Test
    @DisplayName("헤더에 Cookie 가 존재하지 않을경우 파싱하지 않는다")
    void parse_withoutCookieHeaders() {
        final HttpHeaders headers = new HttpHeaders(Map.of("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7"));

        final HttpCookie actual = HttpCookieParser.parse(headers);

        assertThat(actual).isNull();
    }

}