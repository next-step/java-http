package org.apache.catalina.manager;

import org.apache.coyote.http11.model.HttpSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class SessionManager {

    private static final Map<String, HttpSession> SESSIONS = new ConcurrentHashMap<>(); // 동시성 이슈

    public static HttpSession add(HttpSession session){
        SESSIONS.put(session.getId(), session);
        return SESSIONS.get(session.getId());
    }

    public static Optional<HttpSession> findSession(String id) {
        return Optional.ofNullable(SESSIONS.get(id));
    }

    public static void remove(HttpSession session) {
        SESSIONS.remove(session.getId());
    }

    private SessionManager() {
    }
}
