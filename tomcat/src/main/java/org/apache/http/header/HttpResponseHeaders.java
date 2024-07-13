package org.apache.http.header;

import java.util.*;
import java.util.stream.Collectors;

public class HttpResponseHeaders {
    private final static String DELIMITER = "\r\n";
    private final static List<Class<? extends HttpResponseHeader>> OUTPUT_ORDERING_LIST = Arrays.asList(ContentType.class, ContentLength.class, Location.class, SetCookie.class);
    private final static Comparator<HttpResponseHeader> COMPARATOR = Comparator.comparingInt(a -> OUTPUT_ORDERING_LIST.indexOf(a.getClass()));

    private final Set<HttpResponseHeader> headers;


    public HttpResponseHeaders() {
        this.headers = new HashSet<>();
    }

    HttpResponseHeaders(Set<HttpResponseHeader> headers) {
        this.headers = headers;
    }

    public HttpResponseHeaders add(HttpResponseHeader header) {
        final Set<HttpResponseHeader> newHeaders = new HashSet<>(headers);
        newHeaders.add(header);
        return new HttpResponseHeaders(newHeaders);
    }

    @Override
    public String toString() {
        return headers.stream()
                .sorted(COMPARATOR)
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
