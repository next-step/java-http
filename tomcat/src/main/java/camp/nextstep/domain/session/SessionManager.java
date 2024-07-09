package camp.nextstep.domain.session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    private SessionManager() {
        throw new AssertionError();
    }

    public static Optional<Session> findSession(String id) {
        return Optional.ofNullable(SESSIONS.get(id));
    }


    public static void add(Session session) {
        SESSIONS.put(session.getId(), session);
    }
}
