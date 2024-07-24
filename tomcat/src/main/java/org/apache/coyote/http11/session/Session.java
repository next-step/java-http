package org.apache.coyote.http11.session;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionContext;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Session implements HttpSession {
    private final String id;

    private final Map<String, Object> attributes;

    public Session(final String id) {
        this.id = id;
        this.attributes = new HashMap<>();
    }
    @Override
    public long getCreationTime() {
        return 0;
    }

    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int i) {

    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getAttribute(final String name) {
        return attributes.get(name);
    }

    @Override
    public Object getValue(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    public void setAttribute(final String name, final Object value) {
        attributes.put(name, value);
    }

    @Override
    public void putValue(String s, Object o) {

    }

    public void removeAttribute(final String name) {
    }

    @Override
    public void removeValue(String s) {

    }

    public void invalidate() {
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(attributes, session.attributes);
    }
}
