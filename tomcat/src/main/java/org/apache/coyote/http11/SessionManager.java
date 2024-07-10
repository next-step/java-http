package org.apache.coyote.http11;

import org.apache.catalina.Manager;
import org.apache.catalina.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SessionManager implements Manager {
    public static final SessionManager INSTANCE = new SessionManager();

    private SessionManager() {
    }

    private static final Map<String, Session> SESSIONS = new HashMap<>();

    @Override
    public void add(Session session) {
        SESSIONS.put(session.getId(), session);
    }

    @Override
    public Session findSession(String id) throws IOException {
        if (id == null) return null;
        return SESSIONS.get(id);
    }

    @Override
    public void remove(Session session) {
        SESSIONS.remove(session.getId());
    }
}
