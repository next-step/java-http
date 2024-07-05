package camp.nextstep.domain.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryString {

    private static final String QUERY_STRING_FORMAT_SPLIT_REGEX = "&";
    private static final String QUERY_PARAMETER_FORMAT_SPLIT_REGEX = "=";
    private static final int QUERY_PARAMETER_FORMAT_LENGTH = 2;

    private static final int QUERY_PARAMETER_KEY_INDEX = 0;
    private static final int QUERY_PARAMETER_VALUE_INDEX = 1;

    private final Map<String, String> queryParameters;

    public QueryString(String queryString) {
        this.queryParameters = Arrays.stream(queryString.split(QUERY_STRING_FORMAT_SPLIT_REGEX))
                .map(QueryString::splitQueryParameter)
                .collect(Collectors.toMap(
                        queryParameter -> queryParameter[QUERY_PARAMETER_KEY_INDEX],
                        queryParameter -> queryParameter[QUERY_PARAMETER_VALUE_INDEX]
                ));
    }

    private static String[] splitQueryParameter(String queryParameter) {
        String[] splitQueryParameter = queryParameter.split(QUERY_PARAMETER_FORMAT_SPLIT_REGEX);
        if (splitQueryParameter.length != QUERY_PARAMETER_FORMAT_LENGTH) {
            throw new IllegalArgumentException("QueryParameter값이 정상적으로 입력되지 않았습니다 - " + queryParameter);
        }
        return splitQueryParameter;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }
}
