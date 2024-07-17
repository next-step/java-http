package org.apache.coyote.http11;

import org.apache.catalina.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionManagerTest {

    @BeforeEach
    void clearAllSessions() {
        SessionManager.INSTANCE.clearAllSessions();
    }

    @Test
    void add_전에는_findSession_으로_세션을_조회해도_null이_리턴된다() {
        String sessionId = randomUUID();

        assertThat(findSession(sessionId)).isNull();
    }

    private static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    private static Session findSession(String sessionId) {
        return SessionManager.INSTANCE.findSession(sessionId);
    }

    @Test
    void add_후에_findSession_으로_세션을_조회한다() {
        String sessionId = randomUUID();
        Session session = new Session(sessionId);
        add(session);

        assertThat(findSession(sessionId)).isEqualTo(session);
    }

    private static void add(Session session) {
        SessionManager.INSTANCE.add(session);
    }

    @Test
    void 동일한_키로_add를_실행하면_예외발생() {
        String sessionId = randomUUID();
        Session session = new Session(sessionId);

        add(session);

        var exception = assertThrows(IllegalStateException.class, () -> add(session));
        assertThat(exception.getMessage()).isEqualTo("이미 사용 중인 세션 ID 를 사용할 수 없습니다.");
    }

    @Test
    void 세션을_지운다() {
        String sessionId = randomUUID();
        Session session = new Session(sessionId);
        add(session);

        remove(session);

        assertThat(findSession(sessionId)).isNull();
    }

    private void remove(Session session) {
        SessionManager.INSTANCE.remove(session);
    }

    @Test
    void 존재하지_않는_세션을_지우면_예외_발생하지_않는다() {
        String sessionId = randomUUID();
        Session session = new Session(sessionId);

        assertDoesNotThrow(() -> remove(session));
    }

    @Test
    void 세션을_업데이트한다() {
        String sessionId = randomUUID();
        Session session = new Session(sessionId);
        add(session);

        Session newSession = new Session(sessionId);
        newSession.setAttribute("abc", "def");
        update(sessionId, newSession);
        Session actual = findSession(sessionId);

        assertThat(actual.getAttribute("abc")).isEqualTo("def");
    }

    private void update(String sessionId, Session newSession) {
        SessionManager.INSTANCE.update(sessionId, newSession);
    }

    @Test
    void 존재하지_않는_세션을_업데이트해도_예외_발생하지_않는다() {
        String sessionId = randomUUID();
        Session session = new Session(sessionId);

        update(sessionId, session);

        assertThat(findSession(sessionId)).isNull();
    }
}
