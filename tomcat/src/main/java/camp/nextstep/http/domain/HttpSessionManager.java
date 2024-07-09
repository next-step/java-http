package camp.nextstep.http.domain;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionManager {
    private static final Map<String, HttpSession> SESSIONS = new HashMap<>();

    private HttpSessionManager() {
    }

    public static void add(final HttpSession session) {
        SESSIONS.put(session.getId(), session);
    }

    public static HttpSession findSession(final String id) {
        return SESSIONS.get(id);
    }

    public static void remove(final HttpSession session) {
        SESSIONS.remove(session.getId());
    }
}
