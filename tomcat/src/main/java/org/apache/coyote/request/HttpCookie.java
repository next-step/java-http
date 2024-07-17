package org.apache.coyote.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookie {
    private static final String COOKIES_DELIMITER = "; ";
    private static final String COOKIE_DELIMITER = "=";
    private static final int COOKIE_KEY_INDEX = 0;
    private static final int COOKIE_VALUE_INDEX = 1;
    private static final String SESSION_ID_KEY = "JSESSIONID";

    private final Map<String, String> cookies;

    public HttpCookie() {
        this(new HashMap<>());
    }

    public HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static HttpCookie from(RequestHeaders requestHeaders) {
        if (requestHeaders.containsCookie()) {
            String cookie = requestHeaders.getCookie();
            return new HttpCookie(parseCookies(cookie));
        }
        return new HttpCookie();
    }

    private static Map<String, String> parseCookies(String cookie) {
        return Arrays.stream(cookie.split(COOKIES_DELIMITER))
                .map(cookiePart -> cookiePart.split(COOKIE_DELIMITER, 2))
                .collect(Collectors.toMap(
                        cookieKeyValue -> cookieKeyValue[COOKIE_KEY_INDEX],
                        cookieKeyValue -> cookieKeyValue[COOKIE_VALUE_INDEX]
                ));
    }

    public boolean containsSessionId() {
        return cookies.containsKey(SESSION_ID_KEY);
    }

    public String getSessionId() {
        if (!containsSessionId()) {
            throw new IllegalStateException("Session ID not found in cookies");
        }
        return cookies.get(SESSION_ID_KEY);
    }
}
