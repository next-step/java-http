package org.apache.coyote.request;

import org.apache.exception.BadHeaderException;

public class RequestHeader {
    public static final String REQUEST_HEADER_DELIMITER = ": ";
    public static final int REQUEST_HEADER_KEY_INDEX = 0;
    private static final int REQUEST_HEADER_VALUE_INDEX = 1;

    private final String key;
    private final String value;

    public RequestHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static RequestHeader parse(String requestHeader) {
        String[] headerParts = requestHeader.split(REQUEST_HEADER_DELIMITER);
        if (headerParts.length != 2) {
            throw new BadHeaderException();
        }
        return new RequestHeader(headerParts[REQUEST_HEADER_KEY_INDEX], headerParts[REQUEST_HEADER_VALUE_INDEX]);
    }

    public String getValue() {
        return value;
    }
}
