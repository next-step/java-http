package org.apache.http.session;

import org.apache.catalina.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager implements Manager {
    private static final Map<String, HttpSession> SESSIONS = new HashMap<>();

    @Override
    public void add(HttpSession session) {
        SESSIONS.put(session.id, session);
    }

    @Override
    public HttpSession findSession(String id) {
        return SESSIONS.get(id);
    }

    @Override
    public HttpSession findSession(HttpSession session) {
        if (session == null) {
            return null;
        }
        return SESSIONS.get(session.id);
    }

    public HttpSession findOrCreateSession(HttpSession session) {
        var result = findSession(session);
        if (result == null) {
            return create();
        }
        return result;
    }

    public HttpSession create() {
        var id = UUID.randomUUID().toString();
        var newSession = new HttpSession(id);
        add(newSession);
        return newSession;
    }

    @Override
    public void remove(HttpSession session) {

    }
}
