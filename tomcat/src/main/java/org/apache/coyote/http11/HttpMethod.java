package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum HttpMethod {
        GET, POST;

        private static final Map<String, HttpMethod> NAME_TO_HTTP_METHOD_MAP = Arrays.stream(values())
                .collect(Collectors.toUnmodifiableMap(Enum::name, httpMethod -> httpMethod));

        public static HttpMethod from(String method) {
            String upperCase = method.toUpperCase();
            if (NAME_TO_HTTP_METHOD_MAP.containsKey(upperCase)) {
                return NAME_TO_HTTP_METHOD_MAP.get(upperCase);
            }

            throw new IllegalArgumentException("지원되지 않는 HTTP method. 입력=" + upperCase);
        }
    }
