package org.apache.coyote.http11.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void addSession(Session session) {
        if (!sessions.containsKey(session.getSessionId())) {
            sessions.put(session.getSessionId(), session);
        }
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    private SessionManager() {
    }
}
