package camp.nextstep.http.domain;

import java.util.Map;
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
}
