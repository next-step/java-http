package camp.nextstep.http.domain;

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

    public static HttpCookie createHttpCookie(String cookie) {
        return null;
    }
}
