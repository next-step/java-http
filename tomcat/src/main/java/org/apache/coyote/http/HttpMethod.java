package org.apache.coyote.http;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"), POST("POST");

    private final String method;

    HttpMethod(final String method) {
        this.method = method;
    }

    public static HttpMethod from(final String requestMethod) {
        return Arrays.stream(values())
                .filter(method -> method.method.equals(requestMethod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Method not found: " + requestMethod));
    }

    public String getMethod() {
        return method;
    }
}
