package org.apache.coyote.http11.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.coyote.http11.meta.HttpCookie;

public class SessionManager {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    public static Session getSession(HttpCookie cookies) {
        if (!cookies.hasSessionId()) {
            Session session = addSession();
            cookies.addSessionId(session.getSessionId());
            return session;
        }
        if (!sessions.containsKey(cookies.getJSessionId())) {
            return addSession(cookies.getJSessionId());
        }
        return sessions.get(cookies.getJSessionId());
    }

    public static Session getSession(String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            throw new IllegalArgumentException();
        }
        return sessions.get(sessionId);
    }

    public static void addSession(Session session) {
        sessions.put(session.getSessionId(), session);
    }

    private static Session addSession() {
        Session session = new Session(UUID.randomUUID().toString());
        sessions.put(session.getSessionId(), session);
        return session;
    }

    private static Session addSession(String sessionId) {
        Session session = new Session(sessionId);
        sessions.put(session.getSessionId(), session);
        return session;
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

}
