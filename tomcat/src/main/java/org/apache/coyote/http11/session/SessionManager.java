package org.apache.coyote.http11.session;

import jakarta.servlet.http.HttpSession;
import org.apache.catalina.Manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager implements Manager {
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    @Override
    public void add(HttpSession session) {
        SESSIONS.put(session.getId(), (Session) session);
    }

    @Override
    public HttpSession findSession(String id) {
        final Session session = SESSIONS.get(id);
        return (HttpSession) session;
    }

    @Override
    public void remove(HttpSession session) {
        SESSIONS.remove(session.getId());
    }
}
