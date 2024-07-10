package org.apache.coyote.http11.response;

public enum HttpMethod {

    GET, POST;

    public boolean isGet() {
        return this == GET;
    }

    public boolean isPost() {
        return this == POST;
    }
}
