package org.apache.coyote.request;

import java.util.List;
import java.util.Optional;

public class RequestHeaders {

    private final List<RequestHeader> headers;

    public RequestHeaders(List<RequestHeader> headers) {
        this.headers = headers;
    }

    public static RequestHeaders parse(List<String> headerValues) {
        return new RequestHeaders(headerValues.stream()
                .map(RequestHeader::parse)
                .toList());
    }

    public String getHeader(String key) {
        return headers.stream()
                .filter(header -> header.is(key))
                .findFirst()
                .map(RequestHeader::getValue)
                .orElse(null);
    }

    public Optional<String> findCookie() {
        return headers.stream()
                .filter(RequestHeader::isCookie)
                .map(RequestHeader::getValue)
                .findFirst();
    }
}
