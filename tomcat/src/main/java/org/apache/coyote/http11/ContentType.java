package org.apache.coyote.http11;

public enum ContentType {

    TEXT_HTML("text/html")
    ;

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
