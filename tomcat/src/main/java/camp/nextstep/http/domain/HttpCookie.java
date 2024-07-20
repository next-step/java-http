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

    private String cookie;
    private Map<String, String> cookieMap;
    private JSessionId jsessionId;

    private HttpCookie(String cookie, Map<String, String> cookieMap, JSessionId jsessionId) {
        this.cookie = cookie;
        this.cookieMap = cookieMap;
        this.jsessionId = jsessionId;
    }

    public Map<String, String> getCookieMap() {
        return cookieMap;
    }

    public static HttpCookie createHttpCookie(String cookieStr) {
        String[] httpCookies = COOKIE_SEPARATOR.split(cookieStr);
        Map<String, String> cookieMap = new HashMap<>();
        JSessionId jSessionId = null;
        for (String cookie : httpCookies) {
            String[] cookieKeyValArr = COOKE_KEY_VAL_SEPARATOR.split(cookie);
            String key = cookieKeyValArr[COOKIE_KEY_INDEX].trim();
            String value = cookieKeyValArr[COOKIE_VALUE_INDEX].trim();
            cookieMap.put(key, value);

            if (JSESSIONID_KEY.equals(key)) {
                jSessionId = JSessionId.createJSessionIdByJSessionIdStr(value);
            }
        }
        return new HttpCookie(cookieStr, cookieMap, jSessionId);
    }
}
