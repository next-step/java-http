package org.apache.coyote.http11.exception;

public class RequestProtocolInvalidException extends RuntimeException {

    public RequestProtocolInvalidException(final String errorMessage) {
        super(errorMessage);
    }
}
