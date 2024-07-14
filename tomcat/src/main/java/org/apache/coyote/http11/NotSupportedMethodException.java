package org.apache.coyote.http11;

public class NotSupportedMethodException extends IllegalArgumentException{
    public NotSupportedMethodException(String message) {
        super(message);
    }
}

