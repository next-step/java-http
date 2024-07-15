package org.apache.coyote.http11;

import java.util.Arrays;

public enum ContentType {

    TEXT_HTML("text/html", "html"),
    TEXT_CSS("text/css", "css"),
    TEXT_JS("text/javascript", "js"),
    JSON("application/json", "json"),;

    private final String description;
    private final String extenstion;

    ContentType(String description, String extenstion) {
        this.description = description;
        this.extenstion = extenstion;
    }

    public static ContentType from(String path) {
        return Arrays.stream(ContentType.values())
                .filter(it -> path.endsWith(it.extenstion.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid content type: " + path));
    }

    public String getDescription() {
        return description;
    }
}
