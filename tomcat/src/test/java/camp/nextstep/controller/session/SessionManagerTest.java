package camp.nextstep.controller.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import camp.nextstep.model.User;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionManagerTest {

    @Test
    @DisplayName("세션 등록 이후의 세션을 반환한다.")
    void findSession() {
        UUID randomID = UUID.randomUUID();
        Session sessionCreated = new Session(randomID, new User("account", "password", "email"));
        SessionManager.add(sessionCreated);

        Session session = SessionManager.findSession(randomID.toString());

        assertAll(
            () -> assertThat(session).isEqualTo(sessionCreated)
        );
    }
}
