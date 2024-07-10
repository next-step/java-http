package org.apache.coyote.http11.request;

import java.util.Map;

public class QueryString extends Parameters {
    private QueryString(Map<String, Object> parameters) {
        super(parameters);
    }

    public static QueryString from(String queryString) {
        return new QueryString(parseParameters(queryString));
    }
}
