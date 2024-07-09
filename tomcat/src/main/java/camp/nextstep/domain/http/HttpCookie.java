package camp.nextstep.domain.http;

import camp.nextstep.domain.session.Session;

import java.util.*;
import java.util.stream.Collectors;

public class HttpCookie {

    private static final String COOKIE_HEADER_KEY = "Cookie";
    private static final String SESSION_COOKIE_KEY = "JSESSIONID";

    private static final String HTTP_COOKIES_FORMAT_SPLIT_REGEX = "; ";
    private static final String HTTP_COOKIE_FORMAT_SPLIT_REGEX = "=";
    private static final String HTTP_COOKIE_FORMAT = "%s" + HTTP_COOKIE_FORMAT_SPLIT_REGEX + "%s";

    private static final int HTTP_COOKIE_FORMAT_LENGTH = 2;
    private static final int HTTP_COOKIE_KEY_INDEX = 0;
    private static final int HTTP_COOKIE_VALUE_INDEX = 1;

    private final Map<String, String> cookies;

    public HttpCookie(Map<String, String> cookies) {
        this.cookies = new LinkedHashMap<>(cookies);
    }

    public HttpCookie() {
        this(new HashMap<>());
    }

    public static HttpCookie from(Map<String, String> headers) {
        if (headers.containsKey(COOKIE_HEADER_KEY)) {
            return new HttpCookie(parseCookies(headers.get(COOKIE_HEADER_KEY)));
        }
        return new HttpCookie();
    }

    public static HttpCookie from(String cookies) {
        return new HttpCookie(parseCookies(cookies));
    }

    private static Map<String, String> parseCookies(String cookies) {
        return Arrays.stream(cookies.split(HTTP_COOKIES_FORMAT_SPLIT_REGEX))
                .map(HttpCookie::parseCookie)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private static Map.Entry<String, String> parseCookie(String cookie) {
        String[] splitCookie = cookie.split(HTTP_COOKIE_FORMAT_SPLIT_REGEX);
        if (splitCookie.length != HTTP_COOKIE_FORMAT_LENGTH) {
            throw new IllegalArgumentException("Cookie값이 정상적으로 입력되지 않았습니다 - " + cookie);
        }
        return new AbstractMap.SimpleEntry<>(
                splitCookie[HTTP_COOKIE_KEY_INDEX],
                splitCookie[HTTP_COOKIE_VALUE_INDEX]
        );
    }

    public static HttpCookie sessionCookie(Session session) {
        return new HttpCookie(Map.of(SESSION_COOKIE_KEY, session.getId()));
    }

    public String getSessionId() {
        if (!cookies.containsKey(SESSION_COOKIE_KEY)) {
            throw new IllegalStateException("session id가 없습니다.");
        }
        return cookies.get(SESSION_COOKIE_KEY);
    }

    public void addCookie(HttpCookie cookie) {
        this.cookies.putAll(cookie.cookies);
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    public String getCookieHeaderFormat() {
        return cookies.entrySet()
                .stream()
                .map(cookie -> String.format(HTTP_COOKIE_FORMAT, cookie.getKey(), cookie.getValue()))
                .collect(Collectors.joining(HTTP_COOKIES_FORMAT_SPLIT_REGEX));
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
