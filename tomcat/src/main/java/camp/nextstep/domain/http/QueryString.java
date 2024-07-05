package camp.nextstep.domain.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryString {

    private final Map<String, String> queryParameters;

    public QueryString(String queryString) {
        this.queryParameters = Arrays.stream(queryString.split("&"))
                .map(QueryString::splitQueryParameter)
                .collect(Collectors.toMap(
                        queryParameter -> queryParameter[0],
                        queryParameter -> queryParameter[1]
                ));
    }

    private static String[] splitQueryParameter(String queryParameter) {
        String[] splitQueryParameter = queryParameter.split("=");
        if (splitQueryParameter.length != 2) {
            throw new IllegalArgumentException("QueryParameter값이 정상적으로 입력되지 않았습니다 - " + queryParameter);
        }
        return splitQueryParameter;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }
}
