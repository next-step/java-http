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

    public static ContentType fromPath(String path) {
        return Arrays.stream(values())
                .filter(value -> path.endsWith(value.extension))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("확장자 구분자가 존재하지 않아 확장자를 추출할 수 없습니다 - " + path));
    }

    public String getExtension() {
        return extension;
    }

    public String getUtf8ContentType() {
        return contentType + ";charset=utf-8";
    }
}
