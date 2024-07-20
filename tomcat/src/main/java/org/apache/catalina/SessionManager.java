package org.apache.catalina;



import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SessionManager implements Manager {

    public static final SessionManager INSTANCE = new SessionManager();
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    @Override
    public void add(Session session) {
        SESSIONS.put(session.getId(), session);
    }

    @Override
    public Session findSession(String id) throws IOException {
        return SESSIONS.get(id);
    }

    @Override
    public void remove(Session session) {
        SESSIONS.remove(session.getId());
    }

    public Session getOrCreate(String sessionId) {
        Session session = SESSIONS.getOrDefault(sessionId, new Session(sessionId));
        SESSIONS.put(sessionId, session);
        return session;
    }
}
