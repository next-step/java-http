package camp.nextstep.domain.session;

import java.util.Map;
import java.util.Objects;
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

    public Object getAttribute(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("존재하지 않는 세션의 정보입니다. - " + key);
        }
        return values.get(key);
    }

    public void setAttribute(String key, Object value) {
        values.put(key, value);
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
