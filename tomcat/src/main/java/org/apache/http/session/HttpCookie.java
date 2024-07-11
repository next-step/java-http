package org.apache.http.session;

public class HttpCookie {
    private static final String DELIMITER = "=";
    private static final String JSESSIONID = "JSESSIONID";

    private final String name;
    private final String value;

    public HttpCookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public HttpCookie(String cookie) {
        var token = cookie.split(DELIMITER);
        this.name = token[0];
        this.value = token.length > 1 ? token[1] : null;
    }

    public HttpSession toSession() {
        if (!name.equals(JSESSIONID)) {
            return null;
        }
        return new HttpSession(value);
    }


    @Override
    public String toString() {
        if (value != null) {
            return name + DELIMITER + value;
        }
        return name;
    }

    public static HttpCookie ofSession(final HttpSession session) {
        return new HttpCookie(JSESSIONID, session.id);
    }
}
