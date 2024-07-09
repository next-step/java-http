package org.apache.coyote.http11;

import java.util.List;

public class HttpHeaders {
    private final List<HttpHeader> values;

    private HttpHeaders(final List<HttpHeader> values) {
        this.values = values;
    }

    public static HttpHeaders from(final List<String> httpHeaders) {
        List<HttpHeader> values = httpHeaders.stream()
                .map(HttpHeader::from)
                .toList();

        return new HttpHeaders(values);
    }
}
