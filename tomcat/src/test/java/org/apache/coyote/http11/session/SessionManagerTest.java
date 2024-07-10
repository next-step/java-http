package org.apache.coyote.http11.session;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SessionManager 클래스는")
class SessionManagerTest {

    @DisplayName("세션을 추가할 수 있다.")
    @Test
    void add_Session() {
        // given
        Session session = new Session("sessionId");

        // when
        SessionManager.addSession(session);

        // then
        assertEquals(session, SessionManager.getSession(session.getSessionId()));
    }

    @DisplayName("세션을 제거할 수 있다.")
    @Test
    void remove_Session() {
        // given
        Session session = new Session("sessionId");

        // when
        SessionManager.addSession(session);
        SessionManager.removeSession(session.getSessionId());

        // then
        assertNull(SessionManager.getSession(session.getSessionId()));
    }

    @DisplayName("세션을 가져올 수 있다.")
    @Test
    void get_Session() {
        // given
        Session session = new Session("sessionId");

        // when
        SessionManager.addSession(session);

        // then
        assertEquals(session, SessionManager.getSession(session.getSessionId()));
    }
}
