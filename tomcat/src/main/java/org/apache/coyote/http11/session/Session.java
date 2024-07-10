package org.apache.coyote.http11.session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Session {

    private final String sessionId;
    private final LocalDateTime creationTime;
    private Map<String, Object> attributes;

    public Session(String sessionId) {
        this.sessionId = sessionId;
        this.creationTime = LocalDateTime.now();
        this.attributes = new HashMap<>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Object removeAttribute(String key) {
        return attributes.remove(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
