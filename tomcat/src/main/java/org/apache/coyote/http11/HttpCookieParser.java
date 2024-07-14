package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpCookie;
import org.apache.coyote.http11.model.HttpHeaders;

import java.util.HashMap;

public final class HttpCookieParser {

    public static final String SPACE = " ";

    private HttpCookieParser() {
    }

    public static HttpCookie parse(HttpHeaders headers) {
        if(!headers.hasCookie()) {
            return null;
        }

        final String cookie = headers.get("Cookie");
        String[] parts = cookie.split(SPACE);

        HashMap<String, String> cookieMap = new HashMap<>();
        for(String part : parts) {
            String[] keyValue = part.split("=");
            cookieMap.put(keyValue[0], keyValue[1]);
        }

        return new HttpCookie(cookieMap);
    }
}
