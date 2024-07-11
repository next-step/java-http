package org.apache.coyote.http11;

public class ResourceNotFoundException extends IllegalArgumentException{
    public ResourceNotFoundException() {
        super("not found resources");
    }
}
