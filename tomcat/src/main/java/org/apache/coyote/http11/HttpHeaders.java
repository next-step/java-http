package org.apache.coyote.http11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpHeaders {
    private static final String CRLF = "\r\n";

    private final List<HttpHeader> values = new ArrayList<>();

    private HttpHeaders() {
    }

    private HttpHeaders(final List<HttpHeader> values) {
        this.values.addAll(values);
    }

    public static HttpHeaders from(final List<HttpHeader> httpHeaders) {
        return new HttpHeaders(httpHeaders);
    }

    public static HttpHeaders empty() {
        return new HttpHeaders();
    }

    public void addHeader(HttpHeader httpHeader) {
        values.add(httpHeader);
    }

    public String createMessage() {
        return values.stream()
                .map(HttpHeader::createMessage)
                .collect(Collectors.joining(CRLF));
    }

    public String getHeaderValue(final String name) {
        return values.stream()
                .filter(httpHeader -> httpHeader.equalsName(name))
                .findAny()
                .map(HttpHeader::getValue)
                .orElse("");
    }

    public void replace(final String name, final String value) {
        values.stream()
                .filter(httpHeader -> httpHeader.equalsName(name))
                .findAny()
                .ifPresent(values::remove);

        values.add(HttpHeader.of(name, value));
    }
}
