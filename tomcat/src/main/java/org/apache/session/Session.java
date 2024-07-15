package org.apache.session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Session {
    private static final int DEFAULT_EXPIRATION_TIME = 30;

    private final String id;
    private final LocalDateTime expiredAt;
    private final Map<String, Object> values = new HashMap<>();
    private boolean isNew;

    private Session(final String id, final LocalDateTime expiredAt, final boolean isNew) {
        this.id = id;
        this.expiredAt = expiredAt;
        this.isNew = isNew;
    }

    public static Session from(final String id) {
        LocalDateTime now = LocalDateTime.now();
        return new Session(id, now.plusMinutes(DEFAULT_EXPIRATION_TIME), true);
    }

    public String getId() {
        return id;
    }

    public Object getAttribute(final String name) {
        return values.get(name);
    }

    public void addAttribute(final String name, final Object value) {
        values.put(name, value);
    }

    public void removeAttribute(final String name) {
        values.remove(name);
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isNotValid(final LocalDateTime localDateTime) {
        return expiredAt.isBefore(localDateTime);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Session that = (Session) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
