package camp.nextstep.http.domain;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryParameters {
    private final Map<String, String> queryParams;

    public QueryParameters(final String queryString) {
        this.queryParams = parseQueryString(queryString);
    }

    private Map<String, String> parseQueryString(final String queryString) {
        return Stream.of(queryString.split("&"))
                .map(s -> s.split("=", 2))
                .filter(s -> s.length == 2)
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
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
