package camp.nextstep.domain.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionTest {

    @Test
    void session을_랜덤하게_새로_생성한다() {
        Session actual = Session.createNewSession();
        assertThat(actual.getId()).isNotEmpty();
    }

    @Test
    void 동일한_세션은_id로_비교한다() {
        Session session = new Session("id");
        assertThat(session).isEqualTo(new Session("id"));
    }

    @Test
    void attribute가_없는데_가져가려하면_예외가_발생한다() {
        assertThatThrownBy(() -> new Session("id").getAttribute("empty"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 세션의 정보입니다. - empty");
    }

    @Test
    void attribute를_가져간다() {
        Session session = new Session("id");
        session.setAttribute("name", "jinyoung");
        assertThat(session.getAttribute("name")).isEqualTo("jinyoung");
    }
}
