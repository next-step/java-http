package org.apache.coyote.http11;

import org.apache.session.Session;
import org.apache.session.SessionManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionTest {
    @DisplayName("invalidate는 SessionManager에서 자신을 제거한다.(무효화)")
    @Test
    void invalidate() {
        SessionManager sessionManager = SessionManager.create();
        Session session = Session.of("id", sessionManager);

        session.invalidate();

        Assertions.assertThat(sessionManager.findSession(session.getId())).isNull();
    }
}