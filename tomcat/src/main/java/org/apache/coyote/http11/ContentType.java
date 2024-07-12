package org.apache.coyote.http11;

public enum ContentType {

    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    TEXT_JS("text/javascript")
    ;

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public static ContentType from(String path) {
        if (path.endsWith(".css"))
            return TEXT_CSS;
        if (path.endsWith(".js"))
            return TEXT_JS;
        return TEXT_HTML;
    }

    public String getDescription() {
        return description;
    }
}
