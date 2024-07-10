package camp.nextstep.request;

import java.util.*;

public class QueryParameters {
    private static final QueryParameters EMPTY = new QueryParameters(Collections.unmodifiableMap(new HashMap<>()));

    public static QueryParameters empty() {
        return EMPTY;
    }

    // XXX: 변수명
    private final Map<String, List<Object>> map;

    public QueryParameters(Map<String, List<Object>> map) {
        this.map = map;
    }

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public Object get(String key) {
        return getOne(key).orElse(null);
    }

    public String getString(String key) {
        return getOne(key).map(Object::toString).orElse(null);
    }

    private Optional<Object> getOne(String key) {
        if (!map.containsKey(key)) {
            return Optional.empty();
        }

        return map.get(key)
                .stream()
                .findFirst();
    }

    public List<Object> getAll(String key) {
        return map.get(key);
    }
}
