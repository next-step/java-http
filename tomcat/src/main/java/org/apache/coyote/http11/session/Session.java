package org.apache.coyote.http11.session;

import camp.nextstep.model.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionContext;

import java.util.Enumeration;

public class Session implements HttpSession {
    private final String id;
    private final User user;

    public Session(final String id, final User user) {
        this.id = id;
        this.user = user;
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
        return null;
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
}
