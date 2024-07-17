package org.apache.catalina;


public record ViewModel(String path, Object model, Cookie cookie, Session session) {

    public ViewModel(String path) {
        this(path, null, null, null);
    }

}
