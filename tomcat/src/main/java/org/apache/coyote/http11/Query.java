package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Query {

    private static final String DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, Object> parameters;

    private Query(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Object getParameter(String key) {
        return parameters.get(key);
    }

    public static Query from(String queryString) {
        if (queryString == null || queryString.isBlank()) {
            return new Query(Collections.emptyMap());
        }

        String[] tokens = queryString.split(DELIMITER);
        Map<String, Object> parameter = Arrays.stream(tokens)
            .filter(token -> token.contains(KEY_VALUE_DELIMITER))
            .map(token -> token.split(KEY_VALUE_DELIMITER, 2))
            .collect(Collectors.toUnmodifiableMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));

        return new Query(parameter);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
