package org.apache.coyote.http11.exception;

public class RequestMethodNotFoundException extends RuntimeException {

    public RequestMethodNotFoundException(final String errorMessage) {
        super(errorMessage);
    }
}
