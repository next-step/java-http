package org.apache.coyote.http11;

import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public record HttpQueryParams(Map<String,String> value) {

    public static final String QUERY_PARAM_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    public static HttpQueryParams from(String queryString) {
        if(ObjectUtils.isEmpty(queryString)) return null;

        Map<String, String> result = new HashMap<>();

        String[] pairs = queryString.split(QUERY_PARAM_DELIMITER);
        for (String pair : pairs) {
            String[] keyValue = pair.split(KEY_VALUE_DELIMITER, 2);
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";
            result.put(key, value);
        }

        return new HttpQueryParams(result);
    }
}
