package camp.nextstep.request;

import java.util.*;

public class HttpQueryParameters {
    private static final String QUERY_PARAMS_REGEX_SEPARATOR = "&";
    private static final String QUERY_PARAMS_KEY_VALUE_REGEX_SEPARATOR = "=";

    private static final HttpQueryParameters EMPTY = new HttpQueryParameters(Collections.unmodifiableMap(new HashMap<>()));
    private final Map<String, List<Object>> queryParamsMap;

    private HttpQueryParameters(Map<String, List<Object>> queryParamsMap) {
        this.queryParamsMap = queryParamsMap;
    }

    public static HttpQueryParameters parse(String queryString) {
        if (queryString == null) return EMPTY;

        Map<String, List<Object>> map = new HashMap<>();
        for (String each : queryString.split(QUERY_PARAMS_REGEX_SEPARATOR)) {
            String[] keyAndValue = each.split(QUERY_PARAMS_KEY_VALUE_REGEX_SEPARATOR, 2);

            String key = keyAndValue[0];
            String value = keyAndValue.length == 2 ? keyAndValue[1] : null;

            map.computeIfAbsent(key, s -> new ArrayList<>()).add(value);
        }

        return new HttpQueryParameters(map);
    }

    public boolean hasKey(String key) {
        return queryParamsMap.containsKey(key);
    }

    public List<Object> getAll(String key) {
        return queryParamsMap.get(key);
    }

    public Object get(String key) {
        return getOne(key).orElse(null);
    }

    public String getString(String key) {
        return getOne(key).map(Object::toString).orElse(null);
    }

    private Optional<Object> getOne(String key) {
        if (!queryParamsMap.containsKey(key)) {
            return Optional.empty();
        }

        return queryParamsMap.get(key)
                .stream()
                .findFirst();
    }
}
