package org.apache.coyote.http;

import java.util.Arrays;

public enum ContentType {
    NONE(""),
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html"),
    TEXT_JSON("text/json"),
    TEXT_JAVASCRIPT("text/javascript"),
    TEXT_CSS("text/css"),
    IMAGE_SVG("image/svg+xml"),
    CHARSET_UTF_8("charset=utf-8");

    private final String type;

    ContentType(final String type) {
        this.type = type;
    }

    public String type() {
        return this.type;
    }

    public static ContentType from(final String mimeType) {
        return Arrays.stream(values()).filter(contentType -> contentType.type.contains(mimeType)).findFirst().orElse(NONE);
    }
}
