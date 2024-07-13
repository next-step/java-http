package org.apache.coyote.http11.request.parser;

import org.apache.coyote.http11.request.model.Cookie;
import org.apache.coyote.http11.request.model.Cookies;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CookiesParser {
    public static Cookies parse(Map headers) {
        String cookies = (String) headers.get("Cookie");
        if (cookies == null) {
            return Cookies.emptyCookies();
        }
        List<String> cookiesString = Arrays.asList(cookies.split(";"));
        List<Cookie> list = cookiesString.stream().map(CookiesParser::parseCookie).toList();
        return new Cookies(list);
    }
    public static Cookie parseCookie(String cookie) {
        String[] cookieParts = cookie.split("=");
        return new Cookie(cookieParts[0], cookieParts[1]);
    }
}
