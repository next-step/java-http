package org.apache.coyote.http11.request.model;

import java.util.Arrays;

public enum ContentType {
    TEXT_HTML("html", "text/html"),
    TEXT_CSS("css", "text/css"),
    APPLICATION_JAVASCRIPT("js", "application/javascript");

    private final String extension;
    private final String contentType;

    ContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static ContentType findByExtension(final String extension) {
        return Arrays.stream(values())
                .filter(it -> extension.equals(it.extension))
                .findFirst()
                .orElse(TEXT_HTML);
    }

    public String getContentType() {
        return contentType;
    }
}
