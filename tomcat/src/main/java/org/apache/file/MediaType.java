package org.apache.file;

import java.util.Arrays;

public enum MediaType {
    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    TEXT_JS("text/javascript"),
    SVG("image/svg+xml"),
    FORM_URL_ENCODED("application/x-www-form-urlencoded");

    private final String name;

    MediaType(String type) {
        this.name = type;
    }

    @Override
    public String toString() {
        return name;
    }

    public static MediaType match(String contentType) {
        return Arrays.stream(values())
                .filter(type -> type.name.equals(contentType))
                .findFirst()
                .orElse(TEXT_HTML);
    }
}
