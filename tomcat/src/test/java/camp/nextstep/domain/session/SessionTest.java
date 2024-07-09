package camp.nextstep.domain.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
