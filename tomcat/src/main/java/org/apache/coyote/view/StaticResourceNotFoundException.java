package org.apache.coyote.view;

public class StaticResourceNotFoundException extends RuntimeException {

    public StaticResourceNotFoundException(final String message) {
        super(message);
    }
}
