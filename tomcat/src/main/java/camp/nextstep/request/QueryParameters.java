package camp.nextstep.request;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QueryParameters {
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
