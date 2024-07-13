package org.apache.coyote.http11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultSessionTest {
    @DisplayName("invalidate는 SessionManager에서 자신을 제거한다.(무효화)")
    @Test
    void invalidate() {
        SessionManager sessionManager = new SessionManager();
        DefaultSession session = new DefaultSession("id", sessionManager);

        session.invalidate();

        Assertions.assertThat(sessionManager.findSession(session.getId())).isNull();
    }
}