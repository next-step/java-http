package org.apache.catalina.manager;

import org.apache.coyote.http11.model.HttpSession;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class SessionManager {

    private static final Map<String, HttpSession> SESSIONS = new ConcurrentHashMap<>(); // 동시성 이슈

    public static void add(HttpSession session){
        SESSIONS.put(session.getId(), session);
    }

    public static HttpSession findSession(String id) {
        final Optional<HttpSession> session = Optional.ofNullable(SESSIONS.get(id));
        return session.orElse(null);
    }

    public static void remove(HttpSession session) {
        SESSIONS.remove(session.getId());
    }

    private SessionManager() {
    }
}
