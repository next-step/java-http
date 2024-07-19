package org.apache.coyote.http11.exception;

public class RequestProtocolInvalidException extends IllegalArgumentException {

    public RequestProtocolInvalidException(final String errorMessage) {
        super(errorMessage);
    }
}
