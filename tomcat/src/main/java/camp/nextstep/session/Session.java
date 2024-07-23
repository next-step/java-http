package camp.nextstep.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Session {

  private final String id;
  private final Map<String, Object> values = new ConcurrentHashMap<>();

  public Session(String id) {
    this.id = id;
  }

  public static Session createNewSession() {
    return new Session(String.valueOf(UUID.randomUUID()));
  }

  public void setAttribute(String key, Object value) {
    values.put(key, value);
  }

  public String getId() {
    return id;
  }

}
