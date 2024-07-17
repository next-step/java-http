package org.apache.coyote.http11;

import org.apache.catalina.Manager;
import org.apache.catalina.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager implements Manager {
    public static final SessionManager INSTANCE = new SessionManager();
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    @Override
    public void add(Session session) {
        Session previousValue = SESSIONS.putIfAbsent(session.getId(), session);

        if (previousValue != null) {
            throw new IllegalStateException("이미 사용 중인 세션 ID 를 사용할 수 없습니다.");
        }
    }

    @Override
    public Session findSession(String sessionId) {
        if (sessionId == null) return null;

        return SESSIONS.get(sessionId);
    }

    @Override
    public void remove(Session session) {
        SESSIONS.remove(session.getId());
    }

    public void update(String sessionId, Session session) {
        // 의도치 않게 세션이 생성되는 것을 막기 위해 add 대신 computeIfPresent 사용
        SESSIONS.computeIfPresent(sessionId, (k, v) -> session);
    }

    public void clearAllSessions() {
        SESSIONS.clear();
    }
}
