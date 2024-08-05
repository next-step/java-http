package camp.nextstep.controller.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    private SessionManager() {
    }

    public static void add(final Session session) {
        SESSIONS.put(session.getId(), session);
    }

    public static Session findSession(final String id) {
        return SESSIONS.get(id);
    }

    public static void remove(final String id) {
        SESSIONS.remove(id);
    }

    public static int getSize(){
        return SESSIONS.size();
    }
}
