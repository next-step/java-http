package org.apache.coyote.http11.request.model;

public enum HttpMethod {
    POST,
    GET;

    public static boolean isPostMethod(HttpMethod httpMethod) {
        return httpMethod == POST;
    }

    public static boolean isGetMethod(HttpMethod httpMethod) {
        return httpMethod == GET;
    }
}
