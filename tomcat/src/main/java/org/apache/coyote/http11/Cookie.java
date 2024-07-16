package org.apache.coyote.http11;

public class Cookie {
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String MESSAGE_FORMAT = "%s=%s";
    private static final String JSESSIONID = "JSESSIONID";

    private final String name;
    private final String value;

    private Cookie(final String name, final String value) {
        this.name = name.trim();
        this.value = value.trim();
    }

    public static Cookie of(final String name, final String value) {
        return new Cookie(name, value);
    }

    public static Cookie createJSessionCookie(final String sessionId) {
        return new Cookie(JSESSIONID, sessionId);
    }

    public static Cookie from(final String cookie) {
        String[] keyValue = cookie.split(KEY_VALUE_SEPARATOR);
        String name = keyValue[0];
        String value = getValue(keyValue);
        return new Cookie(name, value);
    }

    private static String getValue(final String[] keyValue) {
        if (keyValue.length < 2) {
            return "";
        }

        return keyValue[1];
    }

    public String createMessage() {
        return MESSAGE_FORMAT.formatted(name, value);
    }

    public String getValue() {
        return value;
    }

    public boolean equalsName(final String name) {
        return this.name.equals(name);
    }
}
