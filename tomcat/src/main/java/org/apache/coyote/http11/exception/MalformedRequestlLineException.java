package org.apache.coyote.http11.exception;

public class MalformedRequestlLineException extends RuntimeException {

    public MalformedRequestlLineException(final String errorMessage) {
        super(errorMessage);
    }
}

