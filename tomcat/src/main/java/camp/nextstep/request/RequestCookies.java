package camp.nextstep.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestCookies {
    private static final String COOKIE_KEY_VALUE_REGEX_SEPARATOR = "=";
    private static final String COOKIES_REGEX_SEPARATOR = "; ";

    private static final RequestCookies EMPTY_OBJECT = new RequestCookies(Collections.unmodifiableMap(new HashMap<>()));

    private final Map<String, Cookie> cookiesMap;

    private RequestCookies(Map<String, Cookie> cookiesMap) {
        this.cookiesMap = cookiesMap;
    }

    public static RequestCookies parse(String cookieValue) {
        if (cookieValue == null) return EMPTY_OBJECT;

        Map<String, Cookie> cookiesMap = new HashMap<>();

        for (String eachCookieString : cookieValue.split(COOKIES_REGEX_SEPARATOR)) {
            String[] keyAndValue = eachCookieString.split(COOKIE_KEY_VALUE_REGEX_SEPARATOR, 2);
            String key = keyAndValue[0];
            String value = keyAndValue[1];
            cookiesMap.put(key, new Cookie(key, value));
        }

        return new RequestCookies(cookiesMap);
    }

    public boolean hasKey(String key) {
        return cookiesMap.containsKey(key);
    }

    public Cookie get(String key) {
        if (!hasKey(key)) return null;
        return cookiesMap.get(key);
    }
}
