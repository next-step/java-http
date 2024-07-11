package org.apache.coyote.http11.model.constant;

import java.util.Arrays;
import java.util.List;

public enum ContentType {
    TEXT_HTML(".html", "text/html"),
    TEXT_CSS(".css", "text/css"),
    APPLICATION_JAVASCRIPT(".js", "application/javascript");

    private final String extension;
    private final String contentType;

    ContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static ContentType findByExtension(final String extension) {
        return Arrays.stream(values())
                .filter(it -> extension.equals(it.extension))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("do not find ContentType"));
    }

    public static boolean isStaticType(final String path) {
        return staticTypes().stream()
                .anyMatch(type -> path.contains(type.extension));
    }

    private static List<ContentType> staticTypes() {
        return List.of(TEXT_CSS, APPLICATION_JAVASCRIPT);
    }

    public String contentType() {
        return this.contentType;
    }

    public String extension() {
        return this.extension;
    }
}
