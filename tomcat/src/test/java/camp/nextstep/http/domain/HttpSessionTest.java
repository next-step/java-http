package camp.nextstep.http.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    @Test
    @DisplayName("동일한 id 를 가진 Session 은 동등한 객체이다")
    void sessionEqualityTest() {
        final HttpSession httpSession = new HttpSession("user1");

        assertThat(httpSession).isEqualTo(new HttpSession("user1"));
    }

    @Test
    @DisplayName("세션에 정보를 저장할 수 있다")
    void sessionSetAttributeTest() {
        final HttpSession httpSession = new HttpSession("user1");

        httpSession.setAttribute("userName", "Jwp");

        assertThat(httpSession.getAttribute("userName")).isEqualTo("Jwp");
    }

    @Test
    @DisplayName("세션에 정보를 제거할 수 있다")
    void sessionRemoveAttributeTest() {
        final HttpSession httpSession = new HttpSession("user1");
        httpSession.setAttribute("userName", "Jwp");

        httpSession.removeAttribute("userName");

        assertThat(httpSession.getAttribute("userName")).isNull();
    }

    @Test
    @DisplayName("세션에 정보를 만료 시킬 수 있다")
    void sessionInvalidateTest() {
        final HttpSession httpSession = new HttpSession("user1");
        httpSession.setAttribute("userName", "Jwp");
        httpSession.setAttribute("password", "password");

        httpSession.invalidate();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(httpSession.getAttribute("userName")).isNull();
            softly.assertThat(httpSession.getAttribute("password")).isNull();
        });
    }

}
