package org.apache.coyote.request;

import org.apache.exception.BadQueryStringException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryString {
    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int QUERY_PARAM_KEY_INDEX = 0;
    private static final int QUERY_PARAM_VALUE_INDEX = 1;
    public static final int KEY_AND_VALUE_LENGTH = 2;

    private final Map<String, String> queryParams;

    public QueryString(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public QueryString() {
        this.queryParams = Collections.emptyMap();
    }

    public static QueryString parse(String queries) {
        Map<String, String> queryParams = Arrays.stream(queries.split(QUERY_PARAM_DELIMITER))
                .map(QueryString::splitQueryString)
                .collect(Collectors.toMap(query -> query[QUERY_PARAM_KEY_INDEX], query -> query[QUERY_PARAM_VALUE_INDEX]));
        return new QueryString(queryParams);
    }

    private static String[] splitQueryString(String query) {
        String[] queryParams = query.split(KEY_VALUE_DELIMITER);
        if (queryParams.length != KEY_AND_VALUE_LENGTH) {
            throw new BadQueryStringException();
        }
        return queryParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String findQueryParam(String key) {
        return queryParams.get(key);
    }
}
