package org.apache.coyote.http11.response;

import org.apache.coyote.http11.cookie.Cookies;
import org.apache.coyote.http11.request.model.ContentType;
import org.apache.coyote.http11.request.parser.CookiesParser;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeaders {
    public static final String DELIMITER = ":";

    final private Map<String, String> headers;
    final private Cookies cookies;

    public ResponseHeaders() {
        this.headers = new LinkedHashMap<>();
        this.cookies = CookiesParser.parse(headers);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setContentType(final ContentType contentType) {
        headers.put("Content-Type", contentType.getContentType());
    }

    public void setContentLength(final int length) {
        headers.put("Content-Length", String.valueOf(length));
    }

    public String convertToString() {
        return cookies.getResponseCookies() + headers.entrySet()
                .stream()
                .map(entry -> String.format("%s%s %s ", entry.getKey(), DELIMITER, entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void setLocation(final String location) {
        headers.put("Location", location);
    }
}
