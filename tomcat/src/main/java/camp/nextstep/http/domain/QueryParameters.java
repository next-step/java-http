package camp.nextstep.http.domain;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryParameters {
    private static final String QUERY_STRING_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int PAIR_LIMIT = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> queryParams;

    public QueryParameters(final String queryString) {
        this.queryParams = parseQueryString(queryString);
    }

    private Map<String, String> parseQueryString(final String queryString) {
        return Stream.of(queryString.split(QUERY_STRING_DELIMITER))
                .map(keyValuePairString -> keyValuePairString.split(KEY_VALUE_DELIMITER, PAIR_LIMIT))
                .filter(keyValuePair -> keyValuePair.length == PAIR_LIMIT)
                .collect(Collectors.toMap(pair -> pair[KEY_INDEX], pair -> pair[VALUE_INDEX]));
    }

    public String get(final String key) {
        return queryParams.get(key);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QueryParameters that = (QueryParameters) o;
        return Objects.equals(queryParams, that.queryParams);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(queryParams);
    }
}
