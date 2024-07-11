package camp.nextstep.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestCookies {
    private static final String COOKIE_KEY_VALUE_REGEX_SEPARATOR = "=";
    private static final String COOKIES_REGEX_SEPARATOR = "; ";

    private static final HttpRequestCookies EMPTY_OBJECT = new HttpRequestCookies(Collections.unmodifiableMap(new HashMap<>()));

    public static HttpRequestCookies parse(String cookieValue) {
        if (cookieValue == null) return EMPTY_OBJECT;

        Map<String, HttpRequestCookie> cookiesMap = new HashMap<>();

        for (String eachCookieString : cookieValue.split(COOKIES_REGEX_SEPARATOR)) {
            String[] keyAndValue = eachCookieString.split(COOKIE_KEY_VALUE_REGEX_SEPARATOR, 2);
            String key = keyAndValue[0];
            String value = keyAndValue[1];
            cookiesMap.put(key, new HttpRequestCookie(key, value));
        }

        return new HttpRequestCookies(cookiesMap);
    }

    private final Map<String, HttpRequestCookie> cookiesMap;

    private HttpRequestCookies(Map<String, HttpRequestCookie> cookiesMap) {
        this.cookiesMap = cookiesMap;
    }

    public boolean hasKey(String key) {
        return cookiesMap.containsKey(key);
    }

    public HttpRequestCookie get(String key) {
        if (!hasKey(key)) return null;
        return cookiesMap.get(key);
    }
}
