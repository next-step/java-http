package camp.nextstep.http.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HttpCookie {
    public static final String COOKIE_KEY = "Cookie";

    private static final String JSESSIONID_KEY = "JSESSIONID";
    private static final int COOKIE_KEY_INDEX = 0;
    private static final int COOKIE_VALUE_INDEX = 1;
    private static final Pattern COOKIE_SEPARATOR = Pattern.compile(";");
    private static final Pattern COOKE_KEY_VAL_SEPARATOR = Pattern.compile("=");
    private static final HttpCookie EMPTY_COOKIE = new HttpCookie();

    private String cookie;
    private Map<String, String> cookieMap;

    private HttpCookie(String cookie, Map<String, String> cookieMap) {
        this.cookie = cookie;
        this.cookieMap = cookieMap;
    }

    public HttpCookie() {
    }

    public Map<String, String> getCookieMap() {
        return cookieMap;
    }

    public String getCookie() {
        return cookie;
    }

    public JSessionId getJsessionId() {
        String jSessionIdStr = cookieMap.get(JSESSIONID_KEY);
        return JSessionId.createJSessionIdByJSessionIdStr(jSessionIdStr);
    }

    public static HttpCookie createHttpCookie(String cookieStr) {
        if (cookieStr == null || cookieStr.isEmpty()) {
            return EMPTY_COOKIE;
        }
        String[] httpCookies = COOKIE_SEPARATOR.split(cookieStr);
        Map<String, String> cookieMap = new HashMap<>();
        for (String cookie : httpCookies) {
            String[] cookieKeyValArr = COOKE_KEY_VAL_SEPARATOR.split(cookie);
            String key = cookieKeyValArr[COOKIE_KEY_INDEX].trim();
            String value = cookieKeyValArr[COOKIE_VALUE_INDEX].trim();
            cookieMap.put(key, value);
        }
        return new HttpCookie(cookieStr, cookieMap);
    }
}
