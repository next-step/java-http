package org.apache.http.header;

import org.apache.file.MediaType;
import org.apache.http.body.HttpBody;
import org.apache.http.body.HttpFormBody;
import org.apache.http.body.HttpTextBody;
import org.apache.http.session.HttpSession;

import java.util.*;
import java.util.stream.Collectors;

public class HttpRequestHeaders {
    private final static String DELIMITER = "\r\n";

    private final Map<RequestHeaderParser, HttpRequestHeader> headers;

    public HttpRequestHeaders() {
        this.headers = Map.of();
    }

    public HttpRequestHeaders(HttpRequestHeader header) {
        this.headers = Map.of(header.getParser(), header);
    }

    public HttpRequestHeaders(List<String> headerMessages) {
        this.headers = headerMessages.stream()
                .map(RequestHeaderParser::match)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(HttpRequestHeader::getParser, result -> result));
    }

    public HttpBody parseBody(final String bodyMessages) {
        var contentType = (ContentType) headers.get(RequestHeaderParser.CONTENT_TYPE);
        if (contentType == null) {
            return null;
        }

        if (contentType.match(MediaType.FORM_URL_ENCODED)) {
            return new HttpFormBody(bodyMessages);
        }

        return new HttpTextBody(bodyMessages);
    }

    public int contentLength() {
        var header = (ContentLength) headers.getOrDefault(RequestHeaderParser.CONTENT_LENGTH, new ContentLength());
        return header.length;
    }

    public HttpSession getSession() {
        var header = (Cookie) headers.getOrDefault(RequestHeaderParser.COOKIE, new Cookie());
        return header.findSession();
    }

    @Override
    public String toString() {
        return headers.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
