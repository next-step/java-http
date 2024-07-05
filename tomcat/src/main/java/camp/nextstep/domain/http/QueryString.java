package camp.nextstep.domain.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryString {

    private final Map<String, String> queryString;

    public QueryString(String queryStrings) {
        this.queryString = Arrays.stream(queryStrings.split("&"))
                .map(QueryString::splitQueryString)
                .collect(Collectors.toMap(
                        value -> value[0],
                        value -> value[1]
                ));
    }

    private static String[] splitQueryString(String queryString) {
        String[] splitQueryString = queryString.split("=");
        if (splitQueryString.length != 2) {
            throw new IllegalArgumentException("QueryString값이 정상적으로 입력되지 않았습니다 - " + queryString);
        }
        return splitQueryString;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }
}
