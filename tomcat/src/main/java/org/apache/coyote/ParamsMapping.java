package org.apache.coyote;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParamsMapping {
    private final Map<String, String> params = new HashMap<>();

    public void toQueryStringMapping(final String queryString) {
        final Map<String, String> queryStringMapping = Arrays.stream(queryString.split("&"))
                .map(param -> param.split("=", 2))
                .collect(Collectors.toMap(
                        pair -> pair[0],
                        pair -> {
                            if (pair.length > 1) {
                                return pair[1];
                            }
                            return "";
                        }
                ));

        this.params.putAll(queryStringMapping);
    }

    public Map<String, String> getParams() {
        return Collections.unmodifiableMap(this.params);
    }

    public String getParam(final String parameterName) {
        return this.params.get(parameterName);
    }
}
