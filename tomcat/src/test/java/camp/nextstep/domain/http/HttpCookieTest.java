package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

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
    void key_value가_잘못된_형태로_입력된_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpCookie("name=jin=young"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cookie값이 정상적으로 입력되지 않았습니다 - name=jin=young");
    }

    @Test
    void query_string의_단일_cookie가_파싱된다() {
        HttpCookie actual = new HttpCookie("name=jinyoung");
        assertThat(actual.getCookies()).isEqualTo(Map.of("name", "jinyoung"));
    }

    @Test
    void 여러_cookies를_파싱한다() {
        HttpCookie actual = new HttpCookie("name=jinyoung; password=1234");
        assertThat(actual.getCookies()).isEqualTo(Map.of("name", "jinyoung", "password", "1234"));
    }
}