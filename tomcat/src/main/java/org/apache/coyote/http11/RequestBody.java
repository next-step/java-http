package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private static final String PARAM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    private final Map<String, String> paramsMap = new HashMap<>();

    private RequestBody() {
    }

    public static RequestBody empty() {
        return new RequestBody();
    }

    public String getValue(final String key) {
        return paramsMap.getOrDefault(key, "");
    }

    public void addBody(final String body) {
        Arrays.stream(body.split(PARAM_SEPARATOR))
                .map(param -> param.split(KEY_VALUE_SEPARATOR))
                .filter(keyValue -> keyValue.length == 2)
                .forEach(keyValue -> paramsMap.put(keyValue[0], keyValue[1]));
    }
}
