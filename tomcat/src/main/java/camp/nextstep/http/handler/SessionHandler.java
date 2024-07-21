package camp.nextstep.http.handler;

import camp.nextstep.http.domain.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionHandler {
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    public void add(Session session) {
        SESSIONS.put(session.getId(), session);
    }

    public void remove(String id) {
        SESSIONS.remove(id);
    }

    public Session findSession(final String id) {
        return SESSIONS.get(id);
    }
}
