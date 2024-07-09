package org.apache.coyote.request;

import java.util.List;

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
}
