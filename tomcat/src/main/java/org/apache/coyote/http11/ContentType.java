package org.apache.coyote.http11;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ContentType {
    TEXT_HTML( "text/html", "html"),
    TEXT_CSS("text/css", "css"),
    TEXT_JS("text/javascript", "js"),
    ;

    private final String type;
    private final String extension;

    ContentType(String type, String extension) {
        this.type = type;
        this.extension = extension;
    }

    private static final Map<String, ContentType> EXTENTION_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(ContentType::getExtension, Function.identity())));

    public static ContentType fromExtension(String extension) {
        return Optional.ofNullable(EXTENTION_MAP.getOrDefault(extension, null))
                .orElseThrow(() -> new IllegalArgumentException("not supported extension: " + extension));
    }

    public String getExtension() {
        return extension;
    }

    public String getType() {
        return type;
    }
}
