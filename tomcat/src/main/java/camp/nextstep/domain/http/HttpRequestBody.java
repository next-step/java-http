package camp.nextstep.domain.http;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

public class HttpRequestBody {

    private static final String REQUEST_BODY_FORMAT_SPLIT_REGEX = "&";
    private static final String REQUEST_BODY_KEY_VALUE_FORMAT_SPLIT_REGEX = "=";

    private static final int REQUEST_BODY_FORMAT_LENGTH = 2;
    private static final int REQUEST_BODY_ENTRY_KEY_INDEX = 0;
    private static final int REQUEST_BODY_ENTRY_VALUE_INDEX = 1;

    private final Map<String, String> values;

    public HttpRequestBody(Map<String, String> values) {
        this.values = values;
    }

    public HttpRequestBody() {
        this(emptyMap());
    }

    public static HttpRequestBody from(String requestBody) {
        return new HttpRequestBody(Arrays.stream(requestBody.split(REQUEST_BODY_FORMAT_SPLIT_REGEX))
                .map(HttpRequestBody::parseRequestEntry)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private static Map.Entry<String, String> parseRequestEntry(String requestBodyEntry) {
        String[] splitRequestBodyEntry = requestBodyEntry.split(REQUEST_BODY_KEY_VALUE_FORMAT_SPLIT_REGEX);
        if (splitRequestBodyEntry.length != REQUEST_BODY_FORMAT_LENGTH) {
            throw new IllegalArgumentException("RequestBody값이 정상적으로 입력되지 않았습니다 - " + requestBodyEntry);
        }
        return new AbstractMap.SimpleEntry<>(
                splitRequestBodyEntry[REQUEST_BODY_ENTRY_KEY_INDEX],
                splitRequestBodyEntry[REQUEST_BODY_ENTRY_VALUE_INDEX]
        );
    }

    public String getValue(String key) {
        if (values.containsKey(key)) {
            return values.get(key);
        }
        throw new IllegalArgumentException("존재하지 않는 데이터입니다.");
    }

    public Map<String, String> getValues() {
        return values;
    }
}
