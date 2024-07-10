package org.apache.http.session;

import org.apache.catalina.Manager;

import java.util.HashMap;
import java.util.Map;

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
    public void remove(HttpSession session) {

    }
}
