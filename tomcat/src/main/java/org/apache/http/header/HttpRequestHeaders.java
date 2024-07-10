package org.apache.http.header;

import org.apache.file.MediaType;
import org.apache.http.body.HttpBody;
import org.apache.http.body.HttpFormBody;
import org.apache.http.body.HttpTextBody;

import java.util.*;
import java.util.stream.Collectors;

public class HttpRequestHeaders {
    private final static String DELIMITER = "\r\n";

    private final Map<RequestHeaderName, HttpRequestHeader> headers;

    public HttpRequestHeaders() {
        this.headers = Map.of();
    }

    public HttpRequestHeaders(HttpRequestHeader header) {
        this.headers = Map.of(header.getHeader().getKey(), header.getHeader().getValue());
    }

    public HttpRequestHeaders(List<String> headerMessages) {
        this.headers = headerMessages.stream()
                .map(RequestHeaderName::match)
                .filter(Optional::isPresent)
                .collect(Collectors.toMap(result -> result.get().getHeader().getKey(), result -> result.get().getHeader().getValue()));
    }

    public HttpBody parseBody(final String bodyMessages) {
        var contentType = (ContentType) headers.get(RequestHeaderName.CONTENT_TYPE);
        if (contentType == null) {
            return null;
        }

        if (contentType.match(MediaType.FORM_URL_ENCODED)) {
            return new HttpFormBody(bodyMessages);
        }
        return new HttpTextBody(bodyMessages);
    }

    public int contentLength() {
        var header = (ContentLength) headers.get(RequestHeaderName.CONTENT_LENGTH);
        if (header == null) {
            return -1;
        }
        return header.length;
    }

    @Override
    public String toString() {
        return headers.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
