package org.apache.catalina;

public record ViewModel(String path, boolean redirect) {

    public ViewModel(String path) {
        this(path, false);
    }
}
