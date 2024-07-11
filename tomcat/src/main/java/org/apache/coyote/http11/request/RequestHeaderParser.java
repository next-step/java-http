package org.apache.coyote.http11.request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestHeaderParser {
    public static RequestHeader createRequestHeader(List<String> requestHeaders) {
        RequestLine requestLine = RequestLineParser.createRequestLine(requestHeaders.get(0));
        Map<String, Object> headers = requestHeaders.stream().skip(1)
                .map(header -> header.split(": ", 2))
                .collect(Collectors.toMap(header -> header[0], header -> header[1]));
        return new RequestHeader(requestLine, headers);
    }
}
