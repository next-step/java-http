package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidContentTypeException;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html;charset=utf-8", ".html"),
    CSS("text/css;charset=utf-8", ".css"),
    JAVASCRIPT("text/javascript;charset=utf-8", ".js");

    private final String type;
    private final String extension;

    ContentType(final String type, final String extension) {
        this.type = type;
        this.extension = extension;
    }

    public static ContentType from(final String contentType) {
        return Arrays.stream(values())
                .filter(type -> type.type.equalsIgnoreCase(contentType))
                .findFirst()
                .orElseThrow(() -> new InvalidContentTypeException("No matching ContentType found for " + contentType));
    }

    public static ContentType from(final HttpPath path) {
        return Arrays.stream(values())
                .filter(type -> type.extension.equalsIgnoreCase(path.getExtension()))
                .findFirst()
                .orElseThrow(() -> new InvalidContentTypeException("No matching ContentType found for " + path.getPath()));
    }

    public String getType() {
        return type;
    }

    public String getExtension() {
        return extension;
    }
}
