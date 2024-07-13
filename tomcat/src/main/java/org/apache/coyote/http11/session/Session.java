package org.apache.coyote.http11.session;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionContext;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Session implements HttpSession {
    private final String id;
    private final Map<String, Object> values = new HashMap<>();

    public Session(final String id) {
        this.id = id;
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
        return values.get(name);
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
        values.put(name, value);
    }

    @Override
    public void putValue(String s, Object o) {

    }

    public void removeAttribute(final String name) {
        values.remove(name);
    }

    @Override
    public void removeValue(String s) {

    }

    public void invalidate() {
        values.clear();
    }

    @Override
    public boolean isNew() {
        return false;
    }
}