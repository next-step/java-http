package camp.nextstep.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestCookies {

    private static final RequestCookies EMPTY_OBJECT = new RequestCookies(Collections.unmodifiableMap(new HashMap<>()));

    public static RequestCookies empty() {
        return EMPTY_OBJECT;
    }

    // XXX: 변수명
    private final Map<String, Cookie> map;

    public RequestCookies(Map<String, Cookie> map) {
        this.map = map;
    }

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public Cookie get(String key) {
        if (!hasKey(key)) return null;
        return map.get(key);
    }
}
