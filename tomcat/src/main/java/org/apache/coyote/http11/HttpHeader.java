package org.apache.coyote.http11;

public class HttpHeader {
    private static final String HEADER_KEY_VALUE_SEPARATOR = ":";

    private final String name;
    private final String value;

    private HttpHeader(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public static HttpHeader from(final String httpHeader) {
        String[] keyValue = httpHeader.split(HEADER_KEY_VALUE_SEPARATOR, 2);
        return new HttpHeader(keyValue[0], keyValue[1]);
    }
}
