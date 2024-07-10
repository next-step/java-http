package org.apache.coyote.http11.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

abstract class Parameters {
    protected static final String DELIMITER = "&";
    protected static final String KEY_VALUE_DELIMITER = "=";
    protected static final int KEY_INDEX = 0;
    protected static final int VALUE_INDEX = 1;
    protected static final int SPLITERATOR_SIZE = 2;

    protected final Map<String, Object> parameters;

    protected Parameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Object getParameter(String parameter) {
        return parameters.get(parameter);
    }

    protected static Map<String, Object> parseParameters(String input) {
        if (input == null || input.isBlank()) {
            return Collections.emptyMap();
        }

        String[] tokens = input.split(DELIMITER);
        return Arrays.stream(tokens)
            .filter(token -> token.contains(KEY_VALUE_DELIMITER))
            .map(token -> token.split(KEY_VALUE_DELIMITER, SPLITERATOR_SIZE))
            .collect(Collectors.toUnmodifiableMap(keyValue -> keyValue[KEY_INDEX], keyValue -> keyValue[VALUE_INDEX]));
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
