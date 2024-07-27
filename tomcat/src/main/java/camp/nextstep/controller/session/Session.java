package camp.nextstep.controller.session;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
    private final UUID uuid;
    private final Map<String, Object> values = new ConcurrentHashMap<>();

    public Session(final UUID uuid, final Object user) {
        values.put("user", user);
        this.uuid = uuid;

    }

    public String getId() {
        return this.uuid.toString();
    }

    public Object getAttribute(final String name) {
        return values.get(name);
    }

    public void invalidate(final String name) {
        SessionManager.remove(this.uuid.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(uuid, session.uuid) && Objects.equals(values, session.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, values);
    }
}
