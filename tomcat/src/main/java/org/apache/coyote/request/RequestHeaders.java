package org.apache.coyote.request;

import org.apache.exception.BadHeaderException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestHeaders {
    private static final String REQUEST_HEADER_DELIMITER = ": ";

    private static final int REQUEST_HEADER_KEY_INDEX = 0;
    private static final int REQUEST_HEADER_VALUE_INDEX = 1;
    private static final int KEY_AND_VALUE_LENGTH = 2;

    private final Map<String, String> headers;

    public RequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeaders parse(List<String> headerValues) {
        return new RequestHeaders(headerValues.stream()
                .map(RequestHeaders::parseHeader)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public static Map.Entry<String, String> parseHeader(String requestHeader) {
        String[] headerParts = requestHeader.split(REQUEST_HEADER_DELIMITER);
        if (headerParts.length != KEY_AND_VALUE_LENGTH) {
            throw new BadHeaderException();
        }
        return Map.entry(headerParts[REQUEST_HEADER_KEY_INDEX], headerParts[REQUEST_HEADER_VALUE_INDEX]);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Optional<String> findCookie() {
        return Optional.ofNullable(headers.get("Cookie"));
    }

    public boolean containsCookie() {
        return headers.containsKey("Cookie");
    }

    public String getCookie() {
        if (!containsCookie()) {
            throw new BadHeaderException("Cookie not found in headers");
        }
        return headers.get("Cookie");
    }
}
