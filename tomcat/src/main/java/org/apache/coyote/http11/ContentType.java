package org.apache.coyote.http11;

public enum ContentType {

    TEXT_HTML("text/html"),
    TEXT_CSS("text/css");

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
