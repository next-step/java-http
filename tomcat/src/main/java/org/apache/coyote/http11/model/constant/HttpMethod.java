package org.apache.coyote.http11.model.constant;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public boolean isGetMethod() {
        return this == GET;
    }

    public boolean isPostMethod() {
        return this == POST;
    }
}
