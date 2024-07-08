package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestTarget {
        private static final String PATH_QUERY_SEPARATOR = "\\?";
        private static final String QUERY_SEPARATOR = "&";
        private static final String QUERY_KEY_VALUE_SEPARATOR = "=";

        private final String path;
        private final Map<String, String> queryParamMap;

        private RequestTarget(final String path, final Map<String, String> queryParamMap) {
            this.path = path;
            this.queryParamMap = queryParamMap;
        }

        public static RequestTarget from(String requestTarget) {
            String[] pathAndQueries = requestTarget.split(PATH_QUERY_SEPARATOR);

            if (pathAndQueries.length > 1) {
                String path = pathAndQueries[0];
                String queries = pathAndQueries[1];

                Map<String, String> queryParamMap = Arrays.stream(queries.split(QUERY_SEPARATOR))
                        .map(query -> query.split(QUERY_KEY_VALUE_SEPARATOR))
                        .collect(Collectors.toUnmodifiableMap(query -> query[0], query -> query[1]));

                return new RequestTarget(path, queryParamMap);
            }

            return new RequestTarget(requestTarget, Map.of());
        }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParamMap() {
        return queryParamMap;
    }
}