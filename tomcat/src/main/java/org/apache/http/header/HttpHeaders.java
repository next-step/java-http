package org.apache.http.header;

import org.apache.file.MediaType;
import org.apache.http.body.HttpBody;
import org.apache.http.body.HttpFormBody;
import org.apache.http.body.HttpTextBody;

import java.util.*;
import java.util.stream.Collectors;

public class HttpHeaders {
    private final static String DELIMITER = "\r\n";
    private final static List<Class<? extends HttpHeader>> OUTPUT_ORDERING_LIST = Arrays.asList(ContentType.class, ContentLength.class);
    private final static Comparator<HttpHeader> COMPARATOR = Comparator.comparingInt(a -> OUTPUT_ORDERING_LIST.indexOf(a.getClass()));

    private final Map<HeaderName, HttpHeader> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public HttpHeaders(List<String> headerMessages) {
        this.headers = headerMessages.stream()
                .map(HeaderName::match)
                .filter(Optional::isPresent)
                .collect(Collectors.toMap(result -> result.get().getHeader().getKey(), result -> result.get().getHeader().getValue()));
    }

    HttpHeaders(Map<HeaderName, HttpHeader> headers) {
        this.headers = headers;
    }

    public HttpHeaders add(HttpHeader header) {
        final Map<HeaderName, HttpHeader> newHeaders = new HashMap<>(headers);
        newHeaders.put(header.getHeader().getKey(), header.getHeader().getValue());
        return new HttpHeaders(newHeaders);
    }

    public HttpBody parseBody(final String bodyMessages) {
        var contentType = (ContentType) headers.get(HeaderName.CONTENT_TYPE);
        if (contentType == null) {
            return null;
        }

        if (contentType.match(MediaType.FORM_URL_ENCODED)) {
            return new HttpFormBody(bodyMessages);
        }
        return new HttpTextBody(bodyMessages);
    }

    @Override
    public String toString() {
        return headers.values().stream()
                .sorted(COMPARATOR)
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
