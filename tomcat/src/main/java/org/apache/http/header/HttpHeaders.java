package org.apache.http.header;

import java.util.*;
import java.util.stream.Collectors;

public class HttpHeaders {
    private final static String DELIMITER = "\r\n";
    private final static List<Class<? extends HttpHeader>> OUTPUT_ORDERING_LIST = Arrays.asList(ContentType.class, ContentLength.class);
    private final static Comparator<HttpHeader> COMPARATOR = Comparator.comparingInt(a -> OUTPUT_ORDERING_LIST.indexOf(a.getClass()));

    public Map<String, HttpHeader> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    HttpHeaders(Map<String, HttpHeader> headers) {
        this.headers = headers;
    }

    public HttpHeaders add(HttpHeader header) {
        final Map<String, HttpHeader> newHeaders = new HashMap<>(headers);
        newHeaders.put(header.getHeaderName(), header);
        return new HttpHeaders(newHeaders);
    }

    @Override
    public String toString() {
        return headers.values().stream()
                .sorted(COMPARATOR)
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
