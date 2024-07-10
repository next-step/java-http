package camp.nextstep.domain.http;

import camp.nextstep.domain.session.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpCookieTest {
    @Test
    void 기본_생성자는_빈_쿼리_파라미터를_가진다() {
        HttpCookie actual = new HttpCookie();
        assertThat(actual.getCookies()).isEmpty();
    }

    @Test
    void headers를_가지고_생성할_때_쿠키가_없으면_빈_쿠키가_생성된다() {
        HttpCookie actual = HttpCookie.from(HttpHeaders.from(List.of("Content-Length: 123")));
        assertThat(actual.getCookies()).isEmpty();
    }

    @Test
    void header를_가지고_쿠키값을_추출하여_생성한다() {
        HttpCookie actual = HttpCookie.from(HttpHeaders.from(List.of("Content-Length: 123", "Cookie: name=jinyoung")));
        assertThat(actual.getCookies()).containsExactly(Map.entry("name", "jinyoung"));
    }

    @Test
    void key_value가_잘못된_형태로_입력된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> HttpCookie.from(HttpHeaders.from(List.of("Cookie: name=jin=young"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cookie값이 정상적으로 입력되지 않았습니다 - name=jin=young");
    }

    @Test
    void query_string의_단일_cookie가_파싱된다() {
        HttpCookie actual = HttpCookie.from(HttpHeaders.from(List.of("Cookie: name=jinyoung")));
        assertThat(actual.getCookies()).isEqualTo(Map.of("name", "jinyoung"));
    }

    @Test
    void 여러_cookies를_파싱한다() {
        HttpCookie actual = HttpCookie.from(HttpHeaders.from(List.of("Cookie: name=jinyoung; password=1234")));
        assertThat(actual.getCookies()).isEqualTo(Map.of("name", "jinyoung", "password", "1234"));
    }

    @Test
    void session_cookie를_생성한다() {
        Session session = Session.createNewSession();
        HttpCookie actual = HttpCookie.sessionCookie(session);
        assertThat(actual.getCookies()).contains(Map.entry("JSESSIONID", session.getId()));
    }

    @Test
    void session_id가_없는데_가져오려하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpCookie().getSessionId())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("session id가 없습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"Cookie: JSESSIONID=key,true", "Cookie: name=jinyoung,false"})
    void session_id가_있는지_확인한다(String givenCookie, boolean expected) {
        boolean actual = HttpCookie.from(HttpHeaders.from(List.of(givenCookie))).containsSessionId();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void session_id를_가져온다() {
        String actual = new HttpCookie(Map.of("JSESSIONID", "656cef62-e3c4-40bc-a8df-94732920ed46")).getSessionId();
        assertThat(actual).isEqualTo("656cef62-e3c4-40bc-a8df-94732920ed46");
    }

    @Test
    void 헤더포멧에_맞게_변환하여_반환한다() {
        String actual = HttpCookie.from(HttpHeaders.from(List.of("Cookie: name=jinyoung; password=1234"))).getCookieHeaderFormat();
        assertThat(actual).isEqualTo("name=jinyoung; password=1234");
    }
}