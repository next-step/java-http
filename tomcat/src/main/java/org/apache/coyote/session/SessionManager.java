package org.apache.coyote.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionManager {

    private static final Map<String, Session> sessions = new HashMap<>();

    public SessionManager() {
        throw new IllegalStateException();
    }

    public static void add(Session session) {
        sessions.put(session.getId(), session);
    }

    public static Optional<Session> findSession(String id) {
        return Optional.ofNullable(sessions.get(id));
    }
}
