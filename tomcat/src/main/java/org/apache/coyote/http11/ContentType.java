package org.apache.coyote.http11;

public enum ContentType {

    TEXT_HTML("text/html"),
    TEXT_CSS("text/css");

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public static ContentType from(String path) {
        return path.endsWith(".html") ? TEXT_HTML : TEXT_CSS;
    }

    public String getDescription() {
        return description;
    }
}
