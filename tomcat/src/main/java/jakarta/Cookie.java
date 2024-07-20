package jakarta;

import org.apache.coyote.http11.HttpCookie;

import java.util.Map;

public class Cookie {

    private Map<String, String> cookieMap;

    public Cookie(Map<String, String> cookieMap) {
        this.cookieMap = cookieMap;
    }

    public static Cookie from(HttpCookie cookie) {
        if (cookie == null) {
            return new Cookie(Map.of());
        }
        return new Cookie(cookie.getMap());
    }

    public static Cookie ofJsessionId(String jsessionId) {
        return new Cookie(Map.of(HttpCookie.JSESSIONID, jsessionId));
    }
}
