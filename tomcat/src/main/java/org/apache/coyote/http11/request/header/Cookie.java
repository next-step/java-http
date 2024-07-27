package org.apache.coyote.http11.request.header;

import java.util.Map;

public class Cookie {
    public final Map<String, String> cookies;

    public Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public Map<String, String> getSession() {
        if (cookies.containsKey("JSESSIONID")) {
            return Map.of("JSESSIONID", cookies.get("JSESSIONID"));
        }

        return null;
    }
}
