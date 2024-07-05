package camp.nextstep.domain.http;

import java.util.Map;

public class QueryString {

    private final Map<String, String> queryString;

    public QueryString(String queryString) {
        String[] splitQueryString = queryString.split("=");
        if (splitQueryString.length != 2) {
            throw new IllegalArgumentException("QueryString값이 정상적으로 입력되지 않았습니다 - " + queryString);
        }
        this.queryString = Map.of(splitQueryString[0], splitQueryString[1]);
    }
}
