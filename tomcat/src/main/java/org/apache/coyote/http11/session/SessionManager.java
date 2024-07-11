package org.apache.coyote.http11.session;

import org.apache.catalina.Manager;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager implements Manager {
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public void add(Session session) {
        SESSIONS.put(session.getId(), session);
    }

    @Override
    public Session findSession(String id) {
        final Optional<Session> session = Optional.ofNullable(SESSIONS.get(id));
        return session.orElse(null);
    }

    @Override
    public void remove(Session session) {
        SESSIONS.remove(session.getId());
    }

    private static class SingletonHelper {
        private static final SessionManager INSTANCE = new SessionManager();
    }
}
