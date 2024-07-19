package org.apache.catalina.manager;

import org.apache.catalina.Manager;
import org.apache.coyote.http11.model.HttpSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager implements Manager {

    private static final SessionManager instance = new SessionManager();
    private static final Map<String, HttpSession> SESSIONS = new ConcurrentHashMap<>(); // 동시성 이슈

    // 유일한 인스턴스를 반환하는 메서드
    public static SessionManager getInstance() {
        return instance;
    }

    public HttpSession add(HttpSession session){
        SESSIONS.put(session.getId(), session);
        return SESSIONS.get(session.getId());
    }

    public Optional<HttpSession> findSession(String id) {
        return Optional.ofNullable(SESSIONS.get(id));
    }

    public void remove(HttpSession session) {
        SESSIONS.remove(session.getId());
    }

    private SessionManager() {
    }
}
