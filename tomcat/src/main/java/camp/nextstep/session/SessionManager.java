package camp.nextstep.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

  private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

  private SessionManager() {
    throw new AssertionError();
  }

  public static Session findSession(final String id) {
    return SESSIONS.get(id);
  }


  public static void add(Session session) {
    SESSIONS.put(session.getId(), session);
  }
}
