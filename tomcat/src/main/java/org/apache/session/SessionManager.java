package org.apache.session;

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
        return SESSIONS.get(id);
    }

    public void remove(final Session session) {
        SESSIONS.remove(session.getId());
    }

    public Session createSession() {
        String uuid = UUID.randomUUID().toString();
        return Session.from(uuid);
    }
}
