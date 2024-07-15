package org.apache.coyote.http11;

public enum HttpMethod {
    POST,
    GET,
    PUT,
    DELETE,
    PATCH,
    ;

    public static boolean isPostMethod(HttpMethod httpMethod) {
        return httpMethod == POST;
    }

    public static boolean isGetMethod(HttpMethod httpMethod) {
        return httpMethod == GET;
    }

    public static boolean hasPostOrPutOrPatchMethod(HttpMethod httpMethod) {
        return httpMethod == POST || httpMethod == PUT || httpMethod == PATCH;
    }
}
