package camp.nextstep.domain.http;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

public class HttpCookie {

    private static final String SESSION_COOKIE_KEY = "JSESSIONID";

    private static final String HTTP_COOKIES_FORMAT_SPLIT_REGEX = "; ";
    private static final String HTTP_COOKIE_FORMAT_SPLIT_REGEX = "=";

    private static final int HTTP_COOKIE_FORMAT_LENGTH = 2;
    private static final int HTTP_COOKIE_KEY_INDEX = 0;
    private static final int HTTP_COOKIE_VALUE_INDEX = 1;

    private final Map<String, String> cookies;

    public HttpCookie() {
        this.cookies = emptyMap();
    }

    public HttpCookie(String cookies) {
        this.cookies = Arrays.stream(cookies.split(HTTP_COOKIES_FORMAT_SPLIT_REGEX))
                .map(this::parseCookie)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, String> parseCookie(String cookie) {
        String[] splitCookie = cookie.split(HTTP_COOKIE_FORMAT_SPLIT_REGEX);
        if (splitCookie.length != HTTP_COOKIE_FORMAT_LENGTH) {
            throw new IllegalArgumentException("Cookie값이 정상적으로 입력되지 않았습니다 - " + cookie);
        }
        return new AbstractMap.SimpleEntry<>(
                splitCookie[HTTP_COOKIE_KEY_INDEX],
                splitCookie[HTTP_COOKIE_VALUE_INDEX]
        );
    }

    public String getSessionId() {
        if (!cookies.containsKey(SESSION_COOKIE_KEY)) {
            throw new IllegalStateException("session id가 없습니다.");
        }
        return cookies.get(SESSION_COOKIE_KEY);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
