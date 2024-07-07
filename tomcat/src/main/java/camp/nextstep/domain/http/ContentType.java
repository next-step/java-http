package camp.nextstep.domain.http;

import java.util.Arrays;

public enum ContentType {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    ;

    private final String extension;
    private final String contentType;

    ContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static ContentType fromExtension(String extension) {
        return Arrays.stream(values())
                .filter(value -> value.extension.equals(extension))
                .findAny()
                .get();
    }
}
