package org.apache.coyote.http11;

public class HttpHeader {
    private static final String HEADER_KEY_VALUE_SEPARATOR = ":";
    private static final String MESSAGE_FORMAT = "%s: %s ";

    private final String name;
    private final String value;

    private HttpHeader(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public static HttpHeader from(final String httpHeader) {
        String[] keyValue = httpHeader.split(HEADER_KEY_VALUE_SEPARATOR, 2);
        return new HttpHeader(keyValue[0].trim(), keyValue[1].trim());
    }

    public static HttpHeader of(final String name, final String value) {
        return new HttpHeader(name, value);
    }

    public String createMessage() {
        return MESSAGE_FORMAT.formatted(name, value);
    }

    public boolean equalsName(final String otherName) {
        return name.equals(otherName);
    }

    public String getValue() {
        return value;
    }
}
