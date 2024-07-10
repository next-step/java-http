package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ContentType {

    HTML(".html", "text/html"),
    CSS(".css", "text/css"),
    JS(".js", "application/javascript"),
    ;

    private final String extension;

    private final String value;

    ContentType(String extension, String value) {
        this.value = value;
        this.extension = extension;
    }

    public String getValue() {
        return value;
    }

    public String getExtension() {
        return extension;
    }

    private static final Map<String, ContentType> CONTENT_TYPES = Arrays.stream(ContentType.values())
                                                                        .collect(Collectors.toMap(ContentType::getExtension, Function.identity()));

    public static ContentType from(String extension) {
        return CONTENT_TYPES.getOrDefault(extension, HTML);
    }
}
