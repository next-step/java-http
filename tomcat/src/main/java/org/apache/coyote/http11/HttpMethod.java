package org.apache.coyote.http11;

import java.util.Locale;

public enum HttpMethod {

    GET,
    POST
    ;

    public static HttpMethod from(String method) {
        if (method == null || method.isEmpty()) {
            throw new IllegalArgumentException("HttpMethod is null or empty: " + method);
        }
        return HttpMethod.valueOf(method.toUpperCase(Locale.ROOT));
    }
}
