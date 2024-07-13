package org.apache.coyote.http;

import java.util.UUID;

public class Cookie {

    private static final String COOKIE_ASSIGNMENT = "=";
    private static final int COOKIE_KEY_VALUE_NUM = 2;
    private static final String JSESSIONID = "JSESSIONID";

    public static Cookie createSessionCookie() {
        return new Cookie(JSESSIONID, UUID.randomUUID().toString());
    }

    private final String name;
    private final String value;

    public Cookie(final String cookieLine) {
        final String[] parsedCookie = cookieLine.split(COOKIE_ASSIGNMENT, COOKIE_KEY_VALUE_NUM);

        this.name = parsedCookie[0];
        this.value = parsedCookie[1];
    }

    public Cookie(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String toCookieLine() {
        return this.name + COOKIE_ASSIGNMENT + this.value;
    }

    public String getName() {
        return this.name;
    }

}
