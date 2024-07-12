package org.apache.coyote.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParamsMapping {
    private static final String QUERY_SEPARATOR = "&";
    private static final String QUERY_ASSIGNMENT = "=";
    private static final String FORM_SEPARATOR = "&";
    private static final String FORM_ASSIGNMENT = "=";
    private static final int KEY_VALUE_NUMBER = 2;
    private static final int KEY_POINT = 0;
    private static final int VALUE_POINT = 1;
    private static final String DEFAULT_PARAM_VALUE = "";

    private final Map<String, String> params = new HashMap<>();

    public void fromQueryString(final String queryString) {
        final Map<String, String> queryStringMapping = Arrays.stream(queryString.split(QUERY_SEPARATOR))
                .map(param -> param.split(QUERY_ASSIGNMENT, KEY_VALUE_NUMBER))
                .collect(Collectors.toMap(ParamsMapping::queryKey, ParamsMapping::queryValue));

        this.params.putAll(queryStringMapping);
    }

    public void fromBodyForm(final String form) {
        final Map<String, String> formStringMapping = Arrays.stream(form.split(FORM_SEPARATOR))
                .map(param -> param.split(FORM_ASSIGNMENT, KEY_VALUE_NUMBER))
                .collect(Collectors.toMap(ParamsMapping::queryKey, ParamsMapping::queryValue));

        this.params.putAll(formStringMapping);
    }

    private static String queryKey(String[] pair) {
        return pair[KEY_POINT];
    }

    private static String queryValue(String[] pair) {
        if (pair.length < KEY_VALUE_NUMBER) {
            return DEFAULT_PARAM_VALUE;
        }
        return pair[VALUE_POINT];
    }

    public Map<String, String> getParams() {
        return Collections.unmodifiableMap(this.params);
    }

    public String getParam(final String parameterName) {
        return this.params.get(parameterName);
    }

    public void addParams(final Map<String, String> params) {
        this.params.putAll(params);
    }
}
