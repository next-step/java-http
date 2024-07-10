package camp.nextstep.http.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {
    private final String id;
    private final Map<String, Object> values = new HashMap<>();

    public HttpSession(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(final String name) {
        return values.get(name);
    }

    public void setAttribute(final String name, final Object value) {
        values.put(name, value);
    }

    public void removeAttribute(final String name) {
        values.remove(name);
    }

    public void invalidate() {
        values.clear();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id) && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, values);
    }
}
