package org.apache.coyote.http11.request;

import org.apache.coyote.http11.HttpCookie;
import org.apache.coyote.http11.constants.HttpCookies;
import org.apache.coyote.http11.constants.HttpFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpRequestHeaders {


    private final Map<String, Object> headers = new HashMap<>();

    public HttpRequestHeaders(List<String> lines) {
        if (lines.isEmpty()) {
            return;
        }
        for (String line : lines) {
            parseField(line);
        }
    }

    public String host() {
        return (String) headers.get(HttpFormat.HEADERS.HOST);
    }


    public HttpCookie getCookie() {
        Object cookie = headers.get(HttpCookies.COOKIE_REQUEST_HEADER_FIELD);
        if (cookie == null) {
            return null;
        }
        return new HttpCookie((String) cookie);
    }

    public Optional<Object> get(String key) {
        return Optional.ofNullable(headers.get(key));
    }

    private void parseField(String line) {
        String[] arr = line.split(HttpFormat.HEADERS.FIELD_VALUE_DELIMITER);
        headers.put(arr[0], arr[1]);
    }
}
