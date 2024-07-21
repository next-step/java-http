package org.apache.coyote.support;

public class ResourceNotFoundException extends IllegalArgumentException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
