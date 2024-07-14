package org.apache.catalina.manager;

import org.apache.catalina.Manager;
import org.apache.coyote.http11.model.HttpSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager implements Manager {

    private static final SessionManager instance = new SessionManager();
    private static final Map<String, HttpSession> SESSIONS = new ConcurrentHashMap<>(); // 동시성 이슈

    public SessionManager() {}

    // 유일한 인스턴스를 반환하는 메서드입니다.
    public static SessionManager getInstance() {
        return instance;
    }

    @Override
    public void add(HttpSession session){
        SESSIONS.put(session.getId(), session);
    }

    @Override
    public HttpSession findSession(String id) {
        final Optional<HttpSession> session = Optional.ofNullable(SESSIONS.get(id));
        return session.orElse(null);
    }

    @Override
    public void remove(HttpSession session) {
        SESSIONS.remove(session.getId());
    }
}
