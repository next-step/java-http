package org.apache.coyote.http11.session;

import jakarta.servlet.http.HttpSession;
import org.apache.catalina.Manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager implements Manager {
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return SingletonHelper.INSTANCE;
    }
    @Override
    public void add(HttpSession session) {
        SESSIONS.put(session.getId(), (Session) session);
    }

    @Override
    public HttpSession findSession(String id) {
        final Session session = SESSIONS.get(id);
        return session;
    }

    @Override
    public void remove(HttpSession session) {
        SESSIONS.remove(session.getId());
    }

    private static class SingletonHelper {
        private static final SessionManager INSTANCE = new SessionManager();
    }
}
