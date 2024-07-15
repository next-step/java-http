package org.apache.session;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    public static SessionManager create() {
        return new SessionManager();
    }

    private SessionManager() {
    }

    public void add(final Session session) {
        SESSIONS.put(session.getId(), session);
    }

    public Session findSession(final String id) {
        Session session = SESSIONS.get(id);
        if (session != null && session.isNotValid(LocalDateTime.now())) {
            remove(session);
            return null;
        }
        return session;
    }

    public void remove(final Session session) {
        SESSIONS.remove(session.getId());
    }

    public Session createSession() {
        String uuid = UUID.randomUUID().toString();
        return Session.from(uuid);
    }
}
