package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidContentTypeException;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html;charset=UTF-8", ".html"),
    CSS("text/css;charset=UTF-8", ".css"),
    JAVASCRIPT("text/javascript;charset=UTF-8", ".js");

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
                .orElseThrow(() -> new InvalidContentTypeException("No matching HttpStatusCode found for " + contentType));
    }

    public static ContentType from(final HttpPath path) {
        if (isExtensionEmpty(path)) {
            return HTML;
        }
        return Arrays.stream(values())
                .filter(type -> type.extension.equalsIgnoreCase(path.getExtension()))
                .findFirst()
                .orElseThrow(() -> new InvalidContentTypeException("No matching HttpStatusCode found for " + path.getPath()));
    }

    private static boolean isExtensionEmpty(final HttpPath path) {
        return path.getExtension() == null || path.getExtension().isEmpty();
    }

    public String getType() {
        return type;
    }
}
