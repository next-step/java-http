package camp.nextstep.domain.session;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    @Test
    void session이_없는_경우_빈값이_반환된다() {
        Optional<Session> actual = SessionManager.findSession("empty");
        assertThat(actual).isEmpty();
    }
}
