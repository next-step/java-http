package org.apache.coyote.http11;

public class ResourceNotFoundException extends IllegalArgumentException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
