package camp.nextstep.http.domain;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestBody {
    private static final String REQUEST_BODY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int PAIR_LIMIT = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> params;

    public RequestBody(final String requestBody) {
        this.params = parseRequestBody(requestBody);
    }

    private Map<String, String> parseRequestBody(final String requestBodyString) {
        return Stream.of(requestBodyString.split(REQUEST_BODY_DELIMITER))
                .map(keyValuePairString -> keyValuePairString.split(KEY_VALUE_DELIMITER, PAIR_LIMIT))
                .filter(keyValuePair -> keyValuePair.length == PAIR_LIMIT)
                .collect(Collectors.toMap(pair -> pair[KEY_INDEX], pair -> pair[VALUE_INDEX]));
    }

    public String get(final String key) {
        return params.get(key);
    }

}
