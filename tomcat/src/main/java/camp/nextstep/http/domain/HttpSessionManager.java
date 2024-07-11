package camp.nextstep.http.domain;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    private static final Map<String, HttpSession> SESSIONS = new ConcurrentHashMap<>();

    private HttpSessionManager() {
    }

    public static void add(final HttpSession session) {
        SESSIONS.put(session.getId(), session);
    }

    public static HttpSession computeIfAbsent(final String id) {
        final String sessionId = Optional.ofNullable(id)
                .orElseGet(() -> UUID.randomUUID().toString());

        return SESSIONS.computeIfAbsent(sessionId, HttpSession::new);
    }

    public static void remove(final HttpSession session) {
        SESSIONS.remove(session.getId());
    }
}
