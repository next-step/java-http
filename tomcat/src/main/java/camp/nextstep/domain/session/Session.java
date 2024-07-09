package camp.nextstep.domain.session;

import java.util.Objects;
import java.util.UUID;

public class Session {

    private final String id;

    public Session(String id) {
        this.id = id;
    }

    public static Session createNewSession() {
        return new Session(String.valueOf(UUID.randomUUID()));
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
