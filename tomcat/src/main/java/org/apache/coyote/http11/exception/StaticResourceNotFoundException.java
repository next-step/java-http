package org.apache.coyote.http11.exception;

public class StaticResourceNotFoundException extends RuntimeException {

    public StaticResourceNotFoundException(String message) {
        super(message);
    }
}
