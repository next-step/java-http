package org.apache.coyote.http11.request.model;

import java.util.Map;

public record RequestHeaders(Map<String, Object> requestHeaders) {

    public int getContentLength() {
        return Integer.parseInt((String) requestHeaders.get("Content-Length"));
    }
}
