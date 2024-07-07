package camp.nextstep.domain.http;

import java.util.Arrays;

public enum ContentType {
    TEXT_HTML(".html", "text/html"),
    TEXT_CSS(".css", "text/css"),
    APPLICATION_JAVASCRIPT(".js", "application/javascript"),
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
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 확장자입니다."));
    }

    public String getContentType() {
        return contentType;
    }
}
