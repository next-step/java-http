package org.apache.coyote.http11;

import java.util.Arrays;

public enum MimeType {

    TEXT_HTML("text/html", "html"),
    TEXT_CSS("text/css", "css"),
    TEXT_JS("text/javascript", "js"),
    JSON("application/json", "json"),;

    private final String description;
    private final String extenstion;

    MimeType(String description, String extenstion) {
        this.description = description;
        this.extenstion = extenstion;
    }

    public static MimeType from(String path) {
        return Arrays.stream(MimeType.values())
                .filter(it -> path.endsWith(it.extenstion.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid content type: " + path));
    }

    public String getDescription() {
        return description;
    }

    public static String extensionPattern() {
        return Arrays.stream(MimeType.values())
                .map(it -> it.extenstion)
                .reduce((a, b) -> a + "|" + b)
                .orElse("");
    }
}
