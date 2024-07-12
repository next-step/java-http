package org.apache.coyote.http11;

public class Cookie {
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String MESSAGE_FORMAT = "%s=%s";

    private final String name;
    private final String value;

    private Cookie(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public static Cookie of(final String name, final String value) {
        return new Cookie(name, value);
    }

    public static Cookie from(final String cookie) {
        String[] keyValue = cookie.split(KEY_VALUE_SEPARATOR);
        return new Cookie(keyValue[0], keyValue[1]);
    }

    public static Cookie empty() {
        return new Cookie("", "");
    }

    public String createMessage() {
        return MESSAGE_FORMAT.formatted(name, value);
    }

    public String getValue() {
        return value;
    }

    public boolean isNotEmpty() {
        return !name.isEmpty();
    }
}
