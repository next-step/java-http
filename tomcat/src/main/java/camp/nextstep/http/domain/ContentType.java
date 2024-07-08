package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidContentTypeException;

import java.util.Arrays;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JAVASCRIPT("text/javascript");

    private final String type;

    ContentType(final String type) {
        this.type = type;
    }

    public static ContentType from(final String contentType) {
        return Arrays.stream(values())
                .filter(type -> type.type.equalsIgnoreCase(contentType))
                .findFirst()
                .orElseThrow(() -> new InvalidContentTypeException("No matching HttpStatusCode found for " + contentType));
    }

    public String getType() {
        return type;
    }
}
