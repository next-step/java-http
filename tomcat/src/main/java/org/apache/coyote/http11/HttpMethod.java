package org.apache.coyote.http11;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum HttpMethod {
    GET,
    POST,
    ;

    private static final Map<String, HttpMethod> VALUE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(HttpMethod::name, Function.identity())));

    public static HttpMethod from(String method) {
        return Optional.ofNullable(VALUE_MAP.getOrDefault(method, null))
                .orElseThrow(() -> new IllegalArgumentException("Invalid HTTP method: " + method));
    }
}
