package org.apache.coyote.http;

import java.util.UUID;

public class Cookie {

    private static final String COOKIE_ASSIGNMENT = "=";
    private static final int COOKIE_KEY_VALUE_NUM = 2;
    private static final String JSESSIONID = "JSESSIONID";
    private static final int COOKIE_NAME_POINT = 0;
    private static final int COOKIE_VALUE_POINT = 1;

    public static Cookie createSessionCookie() {
        return new Cookie(JSESSIONID, UUID.randomUUID().toString());
    }

    private final String name;
    private final String value;

    public Cookie(final String cookieLine) {
        final String[] parsedCookie = cookieLine.split(COOKIE_ASSIGNMENT, COOKIE_KEY_VALUE_NUM);

        this.name = parsedCookie[COOKIE_NAME_POINT];
        this.value = parsedCookie[COOKIE_VALUE_POINT];
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
